package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
public class TransactionsApp {
   public static void main(String[] args) {
      SpringApplication.run(TransactionsApp.class, args);
   }

   private final Playground playground;
   private final Jpa jpa;

   @EventListener(ApplicationStartedEvent.class)
   public void go() {
      System.out.println("============= TRANSACTION:START ==============");
      playground.play();
      System.out.println("============= JPA:ONE ==============");
      jpa.one();
      System.out.println("============= JPA:TWO ==============");
      jpa.two();
      System.out.println("============= END ==============");
   }
}

