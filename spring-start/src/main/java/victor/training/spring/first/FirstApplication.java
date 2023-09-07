package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import victor.training.spring.first.subp.XandY;

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
@SpringBootApplication // tech a @Configuration

// you can disable component scan and manualy @Import the classes you want
//@ComponentScan(basePackages = "nothing")
//@Import({XandY.class, .... })
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private XandY x;

  // if you want MULTIPLE instances of X with different configuration,
  // use a 2..x@Bean
//  @Bean
//  public XandY x(Y y) { // "x"
//    return new XandY(y);
//  }
//  @Bean
//  public XandY x1() { // "x1"
//    return new XandY();
//  }
  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }
}

