package victor.training.sequence;

import io.opentelemetry.sdk.trace.SdkTracerProvider;
import net.ttddyy.dsproxy.support.ProxyDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import javax.sql.DataSource;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

/**
 * Draws a sequence diagram of every test annotated {@link SequenceDiagram}, every HTTP request,
 * and every top-level call into one of your beans (eg work done at startup): who called whom,
 * where each transaction begins, is suspended and ends, and every statement sent to the database.
 * <p>
 * Nothing to change in the application: add the dependency and it switches itself on. Each part
 * needs only what it observes — transactions need spring-tx, HTTP needs Spring MVC, SQL needs a
 * {@link DataSource} bean of any database. See {@link SequenceProperties} for the knobs.
 */
@AutoConfiguration
@ConditionalOnProperty(name = "sequence.enabled", havingValue = "true", matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class SequenceAutoConfiguration {
  private static final String SPRING_DATA_REPOSITORY = "org.springframework.data.repository.Repository";
  private static final String TRANSACTION_LISTENER = "org.springframework.transaction.TransactionExecutionListener";

  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  static DiagramStore sequenceDiagramStore(Environment env) {
    SequenceProperties properties = SequenceProperties.from(env);
    return new DiagramStore(Path.of(properties.outputDir()), properties.keepInMemory());
  }

  @Bean(destroyMethod = "close")
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  static SdkTracerProvider sequenceTracerProvider(DiagramStore store, Environment env) {
    // its own provider, not GlobalOpenTelemetry: coexists with an OpenTelemetry agent or exporter already in the app
    return SdkTracerProvider.builder()
        .addSpanProcessor(new DiagramCollector(new PlantUmlGenerator(SequenceProperties.from(env).maxLabelLength()), store))
        .build();
  }

  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  static SequenceRecorder sequenceRecorder(SdkTracerProvider tracerProvider) {
    return new SequenceRecorder(tracerProvider.get("spring-sequence-diagram"));
  }

  /** Around every public method of your beans, before {@code @Transactional}'s own interceptor. */
  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  static Advisor sequenceCallTracingAdvisor(SequenceRecorder recorder, Environment env, BeanFactory beanFactory) {
    List<String> packages = basePackages(env, beanFactory);
    ClassLoader classLoader = SequenceAutoConfiguration.class.getClassLoader();
    String yourBeans = packages.stream()
        .map(p -> "execution(public * " + p + "..*(..))")
        .collect(Collectors.joining(" || ", "(", ")"));
    String expression = "(" + yourBeans
        + " && !within(" + SequenceAutoConfiguration.class.getPackageName() + "..*)"
        + " && !@annotation(org.springframework.context.annotation.Bean)"
        + " && !execution(* toString()) && !execution(* hashCode()) && !execution(* equals(..)))";
    if (ClassUtils.isPresent(SPRING_DATA_REPOSITORY, classLoader)) {
      // their methods are declared in CrudRepository & co, outside your packages
      expression += " || execution(public * " + SPRING_DATA_REPOSITORY + "+.*(..))";
    }
    BooleanSupplier rollbackOnly = ClassUtils.isPresent(TRANSACTION_LISTENER, classLoader)
        ? TxTracingListener::currentTransactionIsRollbackOnly
        : () -> false;

    AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
    advisor.setExpression(expression);
    advisor.setAdvice(new CallTracingInterceptor(recorder, packages, rollbackOnly));
    advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return advisor;
  }

  private static List<String> basePackages(Environment env, BeanFactory beanFactory) {
    List<String> configured = SequenceProperties.from(env).basePackages();
    if (!configured.isEmpty()) {
      return configured;
    }
    if (AutoConfigurationPackages.has(beanFactory)) {
      return AutoConfigurationPackages.get(beanFactory); // the package of your @SpringBootApplication
    }
    throw new IllegalStateException("No @SpringBootApplication to take the package from: set sequence.base-packages");
  }

  /** Any DataSource bean, any driver: the proxy sits between your code and the pool. */
  @Bean
  static BeanPostProcessor sequenceDataSourceProxy(SequenceRecorder recorder, Environment env) {
    boolean showConnections = SequenceProperties.from(env).showConnections();
    return new BeanPostProcessor() {
      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof DataSource dataSource && !(bean instanceof ProxyDataSource)) {
          String database = beanName.equals("dataSource") ? "DB" : beanName;
          JdbcTracingListener listener = new JdbcTracingListener(recorder, showConnections, database);
          return ProxyDataSourceBuilder.create(dataSource).name(beanName).listener(listener).methodListener(listener).build();
        }
        return bean;
      }
    };
  }

  /**
   * Every {@link Executor} bean carries the diagram to the thread that runs its tasks: the one behind
   * {@code @Async}, or one you pass to {@code CompletableFuture.supplyAsync(task, executor)}.
   */
  @Bean
  static BeanPostProcessor sequenceExecutorHandOff() {
    ThreadHandOff handOff = new ThreadHandOff();
    return new BeanPostProcessor() {
      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (!(bean instanceof Executor)) {
          return bean;
        }
        if (bean instanceof Advised advised && !advised.isFrozen()) {
          advised.addAdvice(0, handOff);
          return bean;
        }
        ProxyFactory proxy = new ProxyFactory(bean);
        // injected by its class (ThreadPoolTaskExecutor) as often as by an interface; but a JDK class can't be subclassed here
        boolean jdkClass = bean.getClass().getName().startsWith("java.");
        proxy.setProxyTargetClass(!jdkClass && !Modifier.isFinal(bean.getClass().getModifiers()));
        proxy.addAdvice(handOff);
        try {
          return proxy.getProxy(bean.getClass().getClassLoader());
        } catch (AopConfigException e) {
          return bean; // its tasks will be drawn as diagrams of their own
        }
      }
    };
  }

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnClass(name = TRANSACTION_LISTENER)
  static class Transactions {
    /** Every transaction manager — Boot's, or one you declared yourself (JDBC, JPA, JTA, …). */
    @Bean
    static BeanPostProcessor sequenceTransactionListener(SequenceRecorder recorder) {
      TxTracingListener listener = new TxTracingListener(recorder);
      return new BeanPostProcessor() {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) {
          if (bean instanceof AbstractPlatformTransactionManager manager) {
            manager.addListener(listener);
          }
          return bean;
        }
      };
    }
  }

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  @ConditionalOnClass(RouterFunction.class)
  @ConditionalOnProperty(name = "sequence.http.enabled", havingValue = "true", matchIfMissing = true)
  static class Web {
    @Bean
    FilterRegistrationBean<HttpTracingFilter> sequenceHttpTracingFilter(SequenceRecorder recorder, Environment env) {
      HttpTracingFilter filter = new HttpTracingFilter(recorder, SequenceProperties.from(env).http().excludedPaths());
      FilterRegistrationBean<HttpTracingFilter> registration = new FilterRegistrationBean<>(filter);
      registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
      return registration;
    }

    @Bean
    RouterFunction<ServerResponse> sequencePage(DiagramStore store) {
      return new SequencePage(store).routes();
    }
  }
}
