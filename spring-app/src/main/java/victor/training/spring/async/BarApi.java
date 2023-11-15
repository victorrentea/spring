package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class BarApi {
   @Autowired
   private BarmanService barmanService;

   // TODO [1] autowire and submit tasks to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Make this endpoint non-blocking
   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      Beer beer = barmanService.pourBeer();
      Vodka vodka = barmanService.pourVodka();

      barmanService.auditCocktail("Dilly");

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
