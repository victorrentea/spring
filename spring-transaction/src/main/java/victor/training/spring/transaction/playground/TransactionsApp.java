package victor.training.spring.transaction.playground;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@EnableAsync
@SpringBootApplication
public class TransactionsApp implements CommandLineRunner {
   public TransactionsApp(Playground playground) {
      this.playground = playground;
   }

   public static void main(String[] args) {
      SpringApplication.run(TransactionsApp.class, args);
   }

   private final Playground playground;

   // eg @RestController @RabbitListener
   @Override
   public void run(String... args) throws Exception {
      System.out.println("============= TRANSACTION ONE ==============");
//      try {
         playground.transactionOne();
//      } catch (IOException e) {
//         // NEVER do this (empty catch block)
//      }
      System.out.println("============= TRANSACTION TWO ==============");
      playground.transactionTwo();
      System.out.println("============= END ==============");
   }
}

