package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Slf4j
@Service
public class Barman {
   @Async
   public CompletableFuture<Beer> pourBeer() {
      if (true) throw new RuntimeException("NU* MAI E BERE (*YT!#(&*^$%!&#*^$!*@&%^*");

      log.debug("Pouring Beer (SOAP CALL)...");
      ThreadUtils.sleepq(1000);
      return completedFuture(new Beer());
   }
   @Async
   public CompletableFuture<Vodka> pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepq(1000);
      return CompletableFuture.completedFuture(new Vodka());
   }
}
