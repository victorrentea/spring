package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import sub.Y;

@SpringBootApplication // "mosteneste @Configuration din asta"
// nu recomand, dar se poate
@ComponentScan(basePackages = {"sub","victor.training.spring.first"})
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired // Springule, pune-mi aici o instanta de X
  private X x;

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }

  @Bean
  public Y y() {
    return new Y();
  }
}

