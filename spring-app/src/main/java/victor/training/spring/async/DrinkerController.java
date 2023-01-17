package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;

//      private static final ExecutorService threadPool = Executors.newFixedThreadPool(16);
   @Autowired
   private ThreadPoolTaskExecutor executor;

   // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Build a non-blocking web endpoint
   @GetMapping("api/drink")
   public CompletableFuture<DillyDilly> drink() throws Exception { // ii dai lui Spring inapoi promisu tau
      // el va sti sa raspunda clientului cand e gata Dilly FARA SA BLOCHEZE NICI UN THREAD
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

//      Future<Beer> futureBeer = executor.submit(() -> barman.pourBeer());
//      Future<Vodka> futureVodka = executor.submit(() -> barman.pourVodka());

//      promise === CompletableFuture
      log.info("Oare cu ce barman vorbesc: " + barman.getClass());
      CompletableFuture<Beer> beerPromise = barman.pourBeer();
      // asta da omor juniorului: omu vede metoda chemata aici dar daca arunca o exceptie din pourBeer,
      // ex aia nu o poaet pridne aici, ca metoda DE FAPT nu ruleaza cand o chemi
      // cum e posibil?
      CompletableFuture<Vodka> vodkaPromise =  barman.pourVodka();

//      Beer beer = beerPromise.get(); // 1 sec
//      Vodka vodka = vodkaPromise.get(); // 0 sec ca deja e gata vodka cat a turnat berea

      CompletableFuture<DillyDilly> dillyPromise = beerPromise
              .thenCombine(vodkaPromise, (beer, vodka) -> {
                 log.info("Aici ameste licorile");
                 return new DillyDilly(beer, vodka);
              });


      barman.injura("^&!^&*@^$&(%^&*%");

      long t1 = currentTimeMillis();
      log.debug("Got my drinks in {} millis", t1-t0);
      return dillyPromise;
   }
}
