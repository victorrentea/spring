package victor.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class ClientApp {
   public static void main(String[] args) {
       SpringApplication.run(ClientApp.class, args);
   }

   @Scheduled(fixedDelay = 3000)
   public void send() {

   }
}
