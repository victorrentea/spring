package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

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
@EnableConfigurationProperties(WelcomeInfo.class)
//@ComponentScan(basePackages =
//    {"victor.training.spring.subp",
//        "victor.training.spring.first"})
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private X x;

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println("Auto");
    System.out.println(x.logic());
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }
}

