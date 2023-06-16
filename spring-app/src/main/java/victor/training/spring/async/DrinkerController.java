package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;

   @Autowired
   private ThreadPoolTaskExecutor barPool;

//   @PostMapping("accept-payment")
//   public void method() {
//
//   }
   // TODO [1] autowire and submit tasks to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Make this endpoint non-blocking
   @GetMapping("api/drink")
   public CompletableFuture<DillyDilly> drink() throws Exception {
      log.debug("Submitting my order: "+barPool.getClass());
      long t0 = currentTimeMillis();

      // never in spring
//      ExecutorService threadPool = Executors.newFixedThreadPool(2);
      CompletableFuture<Beer> futureBeer = CompletableFuture
          .supplyAsync(() -> barman.pourBeer(), barPool);
      CompletableFuture<Vodka> futureVodka = CompletableFuture
          .supplyAsync(() -> barman.pourVodka(), barPool);

//      Beer beer = futureBeer.get(); // BLOCK threadul tomcatului pt 1 sec
//      Vodka vodka = futureVodka.get(); // BLOCK threadul tomcatului pt 0

      CompletableFuture<DillyDilly> futureDilly = futureBeer.thenCombine(futureVodka,
          (b, v) -> new DillyDilly(b, v));

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
//      futureDilly.thenAccept(dilly -> rsponse.write(dilly))
      return futureDilly;
   }
   //springu dupa tine agata de futureDilly un callback sa scrie pe socket la client peste HTTP
}
// daca vrei poti sa NU blochezi threadul tomcatului, returnand un CompletableFuture
// procesare async non-blocanta Java8

//
