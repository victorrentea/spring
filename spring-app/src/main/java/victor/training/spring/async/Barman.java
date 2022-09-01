package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class Barman {
   @Async("beerPool")
   public CompletableFuture<Beer> pourBeer() {
//      throw new IllegalArgumentException();
      log.debug("Pouring Beer (SOAP CALL)...");
      ThreadUtils.sleepq(1000);
      return CompletableFuture.completedFuture(new Beer());
   }
   @Async("barExecutor")
   public CompletableFuture<Vodka> pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepq(1000);
      return CompletableFuture.completedFuture(new Vodka());
   }

   @Async
   public void curse(String curse) {
      if (curse!=null)
         throw new RuntimeException("kill ya");
   }
}
