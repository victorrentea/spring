package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// - Dependency Injection: field, constructor, method
// - Defining beans: @Component & co, @ComponentScan
// - Cyclic dependencies
// - Startup code: PostConstruct, @EventListener, CommandLineRunner
// - Qualifier, bean names
// - Primary
// - Lombok tricks: @RAC, lombok.copyableAnnotations+=
// ----1h
// - @ComponentScan ok
// - @Autowired List<BeanInterface>
// - Profile (bean & props)
// - ApplicationContext#getBean
// - @Configuration @Bean proxyMethods
// - @Scope

// - @Value(${}) + @ConfigurationProperties

@SpringBootApplication
@ComponentScan(basePackages = {"victor.training.spring.first", "victor.training.spring.sub"})
@RequiredArgsConstructor // Lombok e un virus de Javac care adauga cod pe care tu nu-l scrii
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }


  // DI: Springule, injecteaza aici un o instanta dupa tip din container
//  @Autowired
//  private X x; // 1 istoric: field injection

  private final X x; // 2: ⭐️ constructor injection recomandat
  private final X x2; //
//  public FirstApplication(X x) {
//    this.x = x;
//  }


//  @Autowired // 3 setter injection
//  public void setZ(Z z) {
//    this.z = z;
//  }

  @Override // from CommandLineRunner, chemata de spring cu param de command line
  public void run(String... args) {
    System.out.println(x.logic());
  }
}

