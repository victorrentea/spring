package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

@SpringBootApplication
@RequiredArgsConstructor
public class TransactionsApp {
   public static void main(String[] args) {
      SpringApplication.run(TransactionsApp.class, args);
   }

   private final Playground playground;
   private final Jpa jpa;
   private final Concurrency concurrency;

//   @GetMapping
   @EventListener(ApplicationStartedEvent.class)
   public void start() throws Exception {
      try {
         System.out.println("============= TRANSACTION:START ==============");
         playground.play();

//         System.out.println("============= JPA:ONE ==============");
//         jpa.one();
//         System.out.println("============= JPA:TWO ==============");
//         jpa.two();
//
//         System.out.println("============= CONCURRENCY ==============");
//         List<Callable<Object>> tasks = List.of(concurrency::thread, concurrency::thread);
//         Executors.newCachedThreadPool().invokeAll(tasks);
         System.out.println("============= END ==============");
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}

