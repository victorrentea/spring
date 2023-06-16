package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;

   @Autowired
   private ThreadPoolTaskExecutor barPool;

   // TODO [1] autowire and submit tasks to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Make this endpoint non-blocking
   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order: "+barPool.getClass());
      long t0 = currentTimeMillis();

      // never in spring
//      ExecutorService threadPool = Executors.newFixedThreadPool(2);
      Future<Beer> futureBeer = barman.pourBeer();
      Future<Vodka> futureVodka = barman.pourVodka();

      Beer beer = futureBeer.get(); // BLOCK threadul tomcatului pt 1 sec
      Vodka vodka = futureVodka.get(); // BLOCK threadul tomcatului pt 0

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
