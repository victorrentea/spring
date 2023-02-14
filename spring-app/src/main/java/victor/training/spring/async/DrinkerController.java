package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;

   // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Build a non-blocking web endpoint

   @Autowired
   private ThreadPoolTaskExecutor barPool;

   @GetMapping("api/drink")
   @Timed
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      // HTTP non blocking APIs with Java<21
      // @Async
      // fire-and-forget calls
      // propagation of thread-local data (@Transactional, current security user, Scope(request), sleuth traceID, Logback MDC %X{) over async calls

      CompletableFuture<Beer> futureBeer = supplyAsync(()->barman.pourBeer(), barPool);

      CompletableFuture<Vodka> futureVodka = supplyAsync(() -> barman.pourVodka(), barPool);

      Beer beer = futureBeer.get(); // thows back to you an ex in async methods
      Vodka vodka = futureVodka.get();

      long t1 = currentTimeMillis();
      log.debug("Got my drinks in {} millis", t1-t0);
      return new DillyDilly(beer, vodka);
   }
}
