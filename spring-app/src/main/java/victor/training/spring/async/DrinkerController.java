package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;
   @Autowired
   ThreadPoolTaskExecutor executor;

   // TODO [1] autowire and submit tasks to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Make this endpoint non-blocking
   @GetMapping("api/drink")
   public CompletableFuture<DillyDilly> drink() throws Exception {
      log.debug("Submitting my order");

      CompletableFuture<Beer> beerPromise = supplyAsync(() -> barman.pourBeer(), executor);
      CompletableFuture<Vodka> vodkaPromise = supplyAsync(() -> barman.pourVodka(), executor);

//      // blocant
//      Beer beer = beerPromise.get(); // 1 sec stau
//      Vodka vodka = vodkaPromise.get(); // 0 sec ca deja e gata vodka
//      return new DillyDilly(beer, vodka);

      // non blocant: threadul tomcat termina in millis
      CompletableFuture<DillyDilly> dillyPromise = beerPromise.thenCombine(vodkaPromise,
          (beer, vodka) -> new DillyDilly(beer, vodka));
      return dillyPromise;
   }
}
