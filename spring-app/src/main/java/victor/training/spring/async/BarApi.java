package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BarApi {
   @Autowired
   private BarmanService barmanService;

   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      ExecutorService threadPool = Executors.newFixedThreadPool(2);
      Future<Beer> futureBeer = threadPool.submit(() -> barmanService.pourBeer());
      Future<Vodka> futureVodka = threadPool.submit(() -> barmanService.pourVodka());

      Beer beer = futureBeer.get();
      Vodka vodka = futureVodka.get();

      barmanService.auditCocktail("Dilly");

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
