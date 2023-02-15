package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

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

   @GetMapping("start-task")
   public void getStartTask() {
//      barman.washGlasses(); // fire and forget
//      CompletableFuture.runAsync(() -> barman.washGlasses()); // starve the commonPool
//      CompletableFuture.runAsync(() -> barman.washGlasses(),barPool); // does not log exc by defauly
      barman.washGlasses();
   }

   @GetMapping("api/drink")
   @Timed
   public CompletableFuture<DillyDilly> drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      // HTTP non blocking APIs with Java<21 OK
         // the problem: you are taking >1000 concurrent HTTP incoming requests at one point in time.

      // @Async



      // fire-and-forget calls
      // propagation of thread-local data (@Transactional, current security user, Scope(request), sleuth traceID, Logback MDC %X{) over async calls

      // "promise" === CompletableFuture => non blocking callback based async processing
//      CompletableFuture<Beer> futureBeer = supplyAsync(()->barman.pourBeer(), barPool);
//      CompletableFuture<Vodka> futureVodka = supplyAsync(() -> barman.pourVodka(), barPool);

      // feels too innocent: callign a method.

      ThreadLocalStuff.THREAD_LOCAL_DATA.set("jdoe");
      CompletableFuture<Beer> futureBeer = barman.pourBeer();
      CompletableFuture<Vodka> futureVodka = barman.pourVodka();

//      Beer beer = futureBeer.get(); // thows back to you an ex in async methods
//      Vodka vodka = futureVodka.get(); // very expensive if under heavy load

      CompletableFuture<DillyDilly> futureDilly = futureBeer.thenCombine(futureVodka, DillyDilly::new);

      long t1 = currentTimeMillis();
      log.debug("Got my drinks in {} millis", t1-t0);
//      HttpServletRequest r; r.startAsync() used under the hood
      return futureDilly;
   }
}
