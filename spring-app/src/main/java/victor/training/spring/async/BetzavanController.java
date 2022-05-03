package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class BetzavanController {
   @Autowired
   private Barman barman;

   // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Build a non-blocking web endpoint
   @GetMapping("api/drink")
   public CompletableFuture<DillyDilly> drink() throws Exception {
      log.debug("Submitting my order CUM MERGE? Proxy:  "+ barman.getClass());
      long t0 = currentTimeMillis();

      CompletableFuture<Beer> futureBeer = barman.pourBeer(); // cat dureaza asta? 0ms.
      log.debug("Instant");
      CompletableFuture<Vodka> futureVodka = barman.pourVodka();


      CompletableFuture<DillyDilly> futureDilly = futureBeer.thenCombine(futureVodka,
          (beer, vodka) -> new DillyDilly(beer, vodka));

      long t1 = currentTimeMillis();
      log.debug("Got my drinks in {} millis", t1-t0);
      return futureDilly;
   }
}
