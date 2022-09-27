package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
public class DrinkerController {
   private final Barman barman;

   public DrinkerController(Barman barman) {
      this.barman = barman;
   }

   // Java SE
//   public static final ExecutorService barPool = Executors.newFixedThreadPool(100);

   // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Build a non-blocking web endpoint
   @GetMapping("api/drink")
   public CompletableFuture<DillyDilly> drink() throws Exception {
      log.debug("Submitting my order to " + barman.getClass());
      long t0 = currentTimeMillis();

      CompletableFuture<Beer> futureBeer = barman.pourBeer();
      CompletableFuture<Vodka> futureVodka = barman.pourVodka();

      CompletableFuture<DillyDilly> futureDilly = futureBeer.thenCombine(futureVodka, (b, v) -> new DillyDilly(b, v));

      long t1 = currentTimeMillis();
      log.debug("Got my drinks in {} millis", t1-t0);
//      return futureDillyMono.block();
      return futureDilly;
   }
}
