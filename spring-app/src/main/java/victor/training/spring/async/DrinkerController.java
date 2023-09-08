package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@RequiredArgsConstructor
@Slf4j
@RestController
public class DrinkerController {
   private final Barman barman;
//   @Qualifier("executor") // ok
   private final ThreadPoolTaskExecutor executor;

   // TODO [1] autowire and submit tasks to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Make this endpoint non-blocking
   @GetMapping("api/drink")
   public CompletableFuture<DillyDilly> drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      // promise (JS) === CompletableFuture (Java8+)
      CompletableFuture<Beer> promiseBeer = supplyAsync(() -> barman.pourBeer()); // problem; no trace ID is propagated to threads running pourBeer/Vodka
      // because I am submitting my work to an executor NOT MANAGED(PROXIED/INSTRUMENTED) BY SPRING: global JVM ForkJoinPool.commonPool
      CompletableFuture<Vodka> promiseVodka = supplyAsync(() -> barman.pourVodka());

      CompletableFuture<DillyDilly> promiseDilly = promiseBeer.thenCombineAsync(promiseVodka,
          (beer, vodka) -> new DillyDilly(beer, vodka));

      executor.submit(() -> barman.auditCocktail("Dilly"));
      // "FIRE-AND-FORGET"
      // dear biz, do we need to WAIT for AUDIT when preparing a drink? NO
      // if an error occurs in audit, should we NOT give the drink to the guy? WRONG. give the drink anyway

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return promiseDilly;
   }
}
