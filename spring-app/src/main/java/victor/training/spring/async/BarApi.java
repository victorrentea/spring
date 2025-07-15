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

@Slf4j
@RestController
@RequiredArgsConstructor
public class BarApi {
   private final Barman barman;
   private final ThreadPoolTaskExecutor e;

   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      // REGULA: orice thread folosesti in trarea unui request trebuie sa vina
      // dintr-un ThreadPoolTaskExecutor manageuit de spring
      CompletableFuture<Beer> futureBeer =
          CompletableFuture.supplyAsync(()->barman.pourBeer(), e); // 1s
      Vodka vodka = barman.pourVodka(); // 1s

      var beer = futureBeer.get();
      CompletableFuture.runAsync(()->barman.sendNotification("Dilly"), e); // 0.5s - dureaza timp si/sau mai crapa

      log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(
          beer, // 1s
          vodka); // 0s
   }
}
