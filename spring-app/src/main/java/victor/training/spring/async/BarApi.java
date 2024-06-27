package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class BarApi {
   @Autowired
   private BarmanService barmanService;
   // nu mai ai thread leak dar e descurajat in Spring sa creezi de mana thr pool
   // =>> foloseste thread pool de-al lui spring
//      private  final ExecutorService threadPool = Executors.newFixedThreadPool(2);
   @Autowired
   ThreadPoolTaskExecutor executor;

   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();
      Future<Beer> futureBeer = executor.submit(() -> barmanService.pourBeer("beer"));
      Future<Vodka> futureVodka = executor.submit(() -> barmanService.pourVodka());
      log.debug("Am pornit comenzile " + (System.currentTimeMillis()-t0));
      Beer beer = futureBeer.get();
      log.debug("Am berea "+(System.currentTimeMillis()-t0));
      Vodka vodka = futureVodka.get(); // 0ms waiting , ca mai are un pic 1500(vodka)-1000(bere)
      log.debug("Am vodka "+(System.currentTimeMillis()-t0));

      barmanService.auditCocktail("Dilly");

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
