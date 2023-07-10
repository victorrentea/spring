package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import victor.training.spring.bean.X;

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
@ComponentScan(basePackages = {
    "victor.training.spring.bean",
    "victor.training.spring.first"})
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired // nu mai trebuie sa creez eu ob manual, in creaza zana Spring
  private X x;

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }
}

