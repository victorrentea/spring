package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;

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
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      Future<Beer> futureBeer = executor.submit(() -> barman.pourBeer());
      Future<Vodka> futureVodka = executor.submit(() -> barman.pourVodka());

      Beer beer = futureBeer.get(); // the HTTP thread waits for 1 second here
      Vodka vodka = futureVodka.get(); // the HTTP thread waits for 0 second here, vodka already poured

      executor.submit(() -> barman.auditCocktail("Dilly"));
      // "FIRE-AND-FORGET"
      // dear biz, do we need to WAIT for AUDIT when preparing a drink? NO
      // if an error occurs in audit, should we NOT give the drink to the guy? WRONG. give the drink anyway

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
