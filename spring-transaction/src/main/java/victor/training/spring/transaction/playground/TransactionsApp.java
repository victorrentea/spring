package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.runAsync;

@SpringBootApplication
@RequiredArgsConstructor
public class TransactionsApp {
  public static void main(String[] args) {
    SpringApplication.run(TransactionsApp.class, args);
  }

  private final Transactions transactions;
  private final Jpa jpa;
  private final Concurrency concurrency;
  private final MessageRepo repo;

  @EventListener(ApplicationStartedEvent.class)
  public void start() {
    try {
      System.out.println("⚠️ DB is re-created empty at each restart ⚠️");
      System.out.println("============= START ==============");
      transactions.play();

//      System.out.println("============= JPA:ONE ==============");
//      jpa.one();
//      System.out.println("============= JPA:TWO ==============");
//      jpa.two();

//      System.out.println("============= CONCURRENCY ==============");
//      allOf(runAsync(concurrency::thread), runAsync(concurrency::thread)).join();
      System.out.println("============= END ==============");
    } catch (Exception e) {
      e.printStackTrace();
      // swallow exception to allow app to start
    }
    System.out.println("==== DATABASE CONTENTS http://localhost:8080/h2-console/ ====");
    repo.findAll().forEach(System.out::println);
  }
}

