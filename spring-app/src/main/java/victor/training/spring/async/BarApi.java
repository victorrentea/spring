package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class BarApi {
   private final Barman barman;

   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      ExecutorService pool = Executors.newFixedThreadPool(2);
      Future<Beer> futureBeer = pool.submit(() -> barman.pourBeer());
      Future<Vodka> futureVodka = pool.submit(() -> barman.pourVodka());
      // am plasat 2 comenzi la mall: una la Pep&Pepper pt ea, una la Spartan pt el

      Beer beer = futureBeer.get();
      Vodka vodka = futureVodka.get();

      barman.auditCocktail("Dilly");

      // fire and forget task
      barman.sendEmail("Reporting Dilly");
      // gresit: Mono.fromRunnable(() -> barman.sendEmail("Reporting Dilly")).subscribe();

      log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
