package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import victor.training.spring.pachet.pachet.X;

@SpringBootApplication
@ComponentScan("victor") // scaneaza de la victor/** in jos
public class FirstApplication  {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Bean // defineste un bean cu numele "y"
  public Y y() {
    return new Y();
  }



  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }
}

