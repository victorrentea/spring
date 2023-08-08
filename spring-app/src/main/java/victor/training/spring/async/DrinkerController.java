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
   public CompletableFuture<DillyDilly> drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();
      // NEVER forget to pass a Spring-managed executor as the last arg to any CompletableFuture.xxxAsync method you call!!
      // in order for Spring to be able to INSTRUMENT the executor and pass metadata from the caller to the worker
      // to copy: SpringSecurityContext (identity of the user) to be able to call @Secured methods,
      //  and TraceID (Sleuth) for correlating log entries passed between systems as header of HTTP/Message
      // CompeltalbeFuture === promise in Frontend; you don't block (.get()) but chain
      CompletableFuture<Beer> futureBeer = barman.pourBeer();
      CompletableFuture<Vodka> futureVodka = barman.pourVodka();
//      try {
         barman.closeFiscalDay(); // i never wait to it to comple
//      } catch (IllegalArgumentException e) {
//         throw new RuntimeException(e);// this will NEVER EVER RUN!! PANIC
//      }

//      Beer beer = futureBeer.get(); // 1s the Tomcat threads(1MB of stack memory)
         // waits for beer, instead of serving other requests
//      Vodka vodka = futureVodka.get(); // 0s wait for vodka (is already done)

      CompletableFuture<DillyDilly> futureDilly = futureBeer.thenCombine(futureVodka,
              (beer, vodka) -> new DillyDilly(beer, vodka));

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
//      return futureDilly.get(); //  FAIL again Tomcat still angry
      return futureDilly; // YES! no Tomcat threads are blocked!
   }
}
