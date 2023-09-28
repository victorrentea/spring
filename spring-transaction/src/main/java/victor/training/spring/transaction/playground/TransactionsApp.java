package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TransactionsApp implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(TransactionsApp.class, args);
   }

   private final JpaPlayground playground;

   @Override
   public void run(String... args) throws Exception {
      System.out.println("============= TRANSACTION ONE ==============");
      playground.transactionOne();
      System.out.println("============= TRANSACTION TWO ==============");
      playground.transactionTwo();
      System.out.println("============= END ==============");
   }
}

