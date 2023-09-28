package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;

   // TODO [1] autowire and submit tasks to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Make this endpoint non-blocking
   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      // TODO optimizati viteza
      //  new Thread(new Runnable // niciodata
      //  ExecutorService.... // interzis in Spring
      // CompletableFuture === promise din FE
      CompletableFuture<Beer> beerPromise = supplyAsync(() -> barman.pourBeer());
      CompletableFuture<Vodka> vodkaPromise = supplyAsync(() -> barman.pourVodka());

      // pe CF nu prea da bine faci .get
      Beer beer = beerPromise.get(); //
      Vodka vodka = vodkaPromise.get();

      barman.auditCocktail("Dilly");

      // TODO daca nu mai e bere ?!! Andreea
      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
