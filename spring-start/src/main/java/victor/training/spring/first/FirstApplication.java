package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import victor.training.spring.Y;

@ComponentScan(basePackages = "victor.training.spring")
@SpringBootApplication // tata lor. De aici incepe aplicatia.
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private X x; // Dependency Injection= Spring iti leaga aici o ref la instanta X
  // desi este private Springu poate oricum sa-l scrie folosind magie: Java Reflection

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }

  @Override // from CommandLineRunner
  public void run(String... args) {
//    System.out.println(x.logic());
  }
}
@Configuration
class AltaClasa {
  @Bean
  public Y y() {
    return new Y();
  }
}

