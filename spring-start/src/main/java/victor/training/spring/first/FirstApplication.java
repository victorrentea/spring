package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import victor.training.spring.supb.AnotherBean;
import victor.training.spring.supb.SomeBean;
import victor.training.spring.supb.X;

// - Dependency Injection: field, constructor, method
// - Defining beans: @Component & co, @ComponentScan
// - Cyclic dependencies
// - Startup code: PostConstruct, @EventListener, CommandLineRunner
// - Qualifier, bean names
// - Primary
// - Lombok tricks: @RAC, lombok.copyableAnnotations+=
// ----1h
// - Profile (bean & props)
// - @Autowired List<BeanInterface>
// - ApplicationContext#getBean
// - @Scope
// - @Configuration @Bean proxyMethods

// - @Value(${}) + @ConfigurationProperties
@SpringBootApplication
@Import({
        X.class,
        SomeBean.class,
        AnotherBean.class
})
//@ComponentScan(basePackages = {"victor.training.spring.supb", "victor.training.spring.first"})
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);

//    new SpringApplicationBuilder()
//            .sources(FirstApplication.class)
//            .listeners(
//                    new ApplicationEnvironmentPreparedEvent() {},
//                    new ApplicationPreparedEventHandler(),
//                    new ApplicationContextInitializedEventHandler(),
//                    new ApplicationEnvironmentPreparedEventHandler())
//            .run(args);
  }

  @Autowired
  private X x;

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }
}

