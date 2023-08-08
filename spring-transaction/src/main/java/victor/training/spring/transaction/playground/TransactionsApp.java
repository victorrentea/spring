package victor.training.spring.transaction.playground;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionsApp implements CommandLineRunner {
   public TransactionsApp(Playground playground) {
      this.playground = playground;
   }

   public static void main(String[] args) {
      SpringApplication.run(TransactionsApp.class, args);
   }

   private final Playground playground;

   @Override
   public void run(String... args) throws Exception {
      System.out.println("============= TRANSACTION ONE ==============");
      playground.transactionOne();
      System.out.println("============= TRANSACTION TWO ==============");
      playground.transactionTwo();
      System.out.println("============= END ==============");
   }
}

