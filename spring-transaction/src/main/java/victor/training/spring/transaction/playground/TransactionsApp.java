package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import victor.training.spring.transaction.playground.extra.DBPrinter;

import static java.util.concurrent.CompletableFuture.runAsync;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class TransactionsApp {
    private final PlayDualWrite playDualWrite;
  private final NPlusOne nPlusOne;

  public static void main(String[] args) {
    SpringApplication.run(TransactionsApp.class, args);
  }

  private final PlayTransactions transactions;
  private final PlayJpa jpa;
  private final PlayLocks locks;
  private final MyEntityRepo repo;
  private final DBPrinter dbPrinter;

  @EventListener(ApplicationStartedEvent.class)
  public void start() {
    try {
      log.info("⚠️ DB is re-created empty at each restart ⚠️");
      log.info("============= START EXPERIMENTS ==============");
//      transactions.play();

//      log.info("============= JPA:writeBehind ==============");
//      jpa.writeBehind();
//      log.info("============= JPA:autoSave ==============");
//      jpa.autoSave();
//      log.info("============= JPA:lazyLoading ==============");
//      jpa.lazyLoading();

//        playDualWrite.saveAndSend();

      nPlusOne.insert();
      nPlusOne.export();

//      log.info("============= LOCKS ==============");
//      allOf(runAsync(locks::thread), runAsync(locks::thread)).join();
      log.info("============= END EXPERIMENTS ==============");
    } catch (Exception e) {
      e.printStackTrace();
      // print and ignore exception to allow the application to start
    }
    dbPrinter.print();
  }


}

