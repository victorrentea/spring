package victor.training.spring;

import io.micrometer.context.ContextSnapshot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.web.controller.util.TestDBConnectionInitializer;

import javax.sql.DataSource;
import java.sql.SQLException;

import static java.lang.System.currentTimeMillis;

@SpringBootApplication
@EnableCaching
@Slf4j
@ConfigurationPropertiesScan
@EnableFeignClients
public class SpringApplication {
  public static final long t0 = currentTimeMillis();

  public static void main(String[] args) {
    new SpringApplicationBuilder(SpringApplication.class)
            .listeners(new TestDBConnectionInitializer())
            .run(args);
  }

  @Autowired
  private Environment environment;

  @Autowired
  private DataSource dataSource;

  @Bean // instrumented by micrometer-tracing
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Bean
  public RestClient restClient(RestTemplate restTemplate) {
    return RestClient.builder(restTemplate).build();
  }

  @Bean // propagate tracing over all Spring-managed thread pools
  public TaskDecorator taskDecorator() {
    return (runnable) -> ContextSnapshot.captureAll().wrap(runnable);
  }

  @Bean // enable propagation of SecurityContextHolder over @Async
  public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor poolBar) {
    return new DelegatingSecurityContextAsyncTaskExecutor(poolBar);
  }

  @EventListener(ApplicationStartedEvent.class)
  @Order
  public void printAppStarted() throws SQLException {
    long t1 = currentTimeMillis();
    String jdbcUrl = dataSource.getConnection().getMetaData().getURL();
    log.info("🎈🎈🎈 Application started in {}ms on port :{} connected to DB {} 🎈🎈🎈",
            t1 - t0,
            environment.getProperty("local.server.port"),
            jdbcUrl);
  }

}
