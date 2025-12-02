package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import victor.training.spring.transaction.playground.extra.DBPrinter;

import static java.util.concurrent.CompletableFuture.runAsync;

@EnableScheduling
@Configuration
//@Profile("!test")
@ConditionalOnProperty(
    value = "schedulers.enabled",
    havingValue = "true",
    matchIfMissing = true)
    // in application-test.properties am setat schedulers.enabled=false
class SchedulerConfig {

}

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class TransactionsApp {
  public static void main(String[] args) {
    SpringApplication.run(TransactionsApp.class, args);
  }

  private final PlayTransactions transactions;
  private final PlayJpa jpa;
  private final PlayLocking playLocking;
  private final MessageRepo repo;
  private final DBPrinter dbPrinter;

  @EventListener(ApplicationStartedEvent.class)
  public void start() {
    try {
      log.info("⚠️ DB is re-created empty at each restart ⚠️");
      log.info("============= START EXPERIMENTS ==============");
      log.info("Chem metoda pe un proxy injectat de Spring " + transactions.getClass());
//      transactions.play();
//      transactions.extracted();
      transactions.entry();

      jpa.saveAndSend();


      log.info("============= JPA:writeBehind ==============");
      jpa.writeBehind();
      log.info("============= JPA:autoSave ==============");
      jpa.autoSave();
      log.info("============= JPA:lazyLoading ==============");
      jpa.lazyLoading();

//      log.info("============= LOCKING ==============");
//      allOf(runAsync(concurrency::thread), runAsync(concurrency::thread)).join();
      log.info("============= END EXPERIMENTS ==============");
    } catch (Exception e) {
      e.printStackTrace();
      // swallow exception to allow app to start
    }
    dbPrinter.print();
  }


}

