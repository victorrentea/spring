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
   @Async("barPool") // some HATE it
   public CompletableFuture<Beer> pourBeer() {
      boolean drama = true;
      if (drama) throw new RuntimeException("OMG no beer!");
      log.debug("Pouring Beer (SOAP CALL)...");
      ThreadUtils.sleepq(1000);
      return CompletableFuture.completedFuture(new Beer());
   }
   @Async("barPool")
   public CompletableFuture<Vodka> pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepq(1000);
      return CompletableFuture.completedFuture(new Vodka());
   }
}
