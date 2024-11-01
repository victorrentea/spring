package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BarApi {
   private final Barman barman;
   private final ThreadPoolTaskExecutor poolBar;

   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      Future<Beer> futureBeer = poolBar.submit(barman::pourBeer);
      Future<Vodka> futureVodka = poolBar.submit(barman::pourVodka);
//      CompletableFuture.supplyAsync(barman::pourBeer);

      Beer beer = futureBeer.get();
      Vodka vodka = futureVodka.get();

      barman.auditCocktail("Dilly");
      barman.sendEmail("Reporting Dilly");
      log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
