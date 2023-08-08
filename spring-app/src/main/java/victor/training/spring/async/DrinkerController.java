package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;

   private final ThreadPoolTaskExecutor executor;

   public DrinkerController(ThreadPoolTaskExecutor barPool) {
      this.executor = barPool;
   }

   // TODO [1] autowire and submit tasks to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Make this endpoint non-blocking
   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();


      // NEVER forget to pass a Spring-managed executor as the last arg to any CompletableFuture.xxxAsync method you call!!
      // in order for Spring to be able to INSTRUMENT the executor and pass metadata from the caller to the worker
      // to copy: SpringSecurityContext (identity of the user) to be able to call @Secured methods,
      //  and TraceID (Sleuth) for correlating log entries passed between systems as header of HTTP/Message
      Future<Beer> futureBeer = CompletableFuture.supplyAsync(() -> barman.pourBeer(),executor);
      Future<Vodka> futureVodka = CompletableFuture.supplyAsync(() -> barman.pourVodka(),executor);


      Beer beer = futureBeer.get(); // 1s wait for beer
      Vodka vodka = futureVodka.get(); // 0s wait for vodka (is already done)

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
