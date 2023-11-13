package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import victor.training.spring.first.alt.X;

import java.util.Arrays;

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
@Import(X.class)
//@Import(OClasaDintrunJar.class)
//@ComponentScan(basePackages = {
//    "victor.training.spring.first",
//    "victor.training.spring.alt"})
@ConfigurationPropertiesScan // sa detecteze automat @ConfigurationProperties de peste tot
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }
  @Autowired
  private X x;

  @Override // from CommandLineRunner, good for Batch Jobs
  public void run(String... args) {
    System.out.println("Command line argsx al programului java: " + Arrays.toString(args));
    System.out.println(x.logic());
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }
}

