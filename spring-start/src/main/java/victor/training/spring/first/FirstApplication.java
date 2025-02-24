package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Import({ SecondConfig.class, MyConfig.class})
@EnableConfigurationProperties(Props.class)
@ComponentScan(basePackages = "kodagcidscgdhcgvdsuyfeqw")
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

//  private static FirstApplication INSTANCE;
//
//  public static FirstApplication getInstance() {
//    if (INSTANCE == null) {
//      INSTANCE = new FirstApplication();
//    }
//    return INSTANCE;
//  }
//
//  private FirstApplication() {
//  }
  @Autowired
  private X x;

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }


  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }
}

