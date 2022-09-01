package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class TransactionsApp implements CommandLineRunner {
   public static void main(String[] args) {
      new SpringApplicationBuilder(TransactionsApp.class)
//              .listeners(new InjectP6SpyApplicationListener())
              .run(args);
   }

   private final Playground playground;

//   @Autowired
//   ApplicationContext applicationContext;

   @Override
   public void run(String... args) throws Exception {
      System.out.println("============= TRANSACTION ONE ==============");
      playground.transactionOne();
      System.out.println("============= TRANSACTION TWO ==============");
      playground.transactionTwo();
      System.out.println("============= END ==============");
   }
}

