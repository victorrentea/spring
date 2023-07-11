package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Timed
public class Barman {
//   private String currentUsername; // nu tii pe campurile unui singleton date asoc
//   // requestului curent.
//
//   public void setCurrentUsername(String currentUsername) {
//      this.currentUsername = currentUsername;
//   }
   @Async("executor") // numele beanului Executor pe care lansezi
   public CompletableFuture<Beer> pourBeer() {
      log.debug("Pouring Beer (SOAP CALL) for ...");
      ThreadUtils.sleepMillis(1000);
      return CompletableFuture.completedFuture(new Beer());
   }
   public Vodka pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepMillis(1000);
      return new Vodka();
   }

   @Async
   public void process(String dateDePusInHazelcast) throws InterruptedException {
      log.info("START");
      Thread.sleep(3000);
      log.info("DONE");
   }
}
