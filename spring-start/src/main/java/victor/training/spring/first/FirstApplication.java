package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import victor.training.spring.X;

//@ConfigurationPropertiesScan //1
@EnableConfigurationProperties(Props.class) //2

//@ComponentScan("victor.training")// Fix#1
//@Import(X.class) // Fix#2
// explicit @Bean

@SpringBootApplication
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private X x;

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic("userul din request"));
  }
}

@RequiredArgsConstructor
class AltBeanCreatDeMana {
  private final X x;
}