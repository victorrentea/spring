package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      CompletableFuture<Beer> futureBeer = CompletableFuture.supplyAsync(()->barman.pourBeer()); // 1s
      Vodka vodka = barman.pourVodka(); // 1s

      var beer = futureBeer.get();
      barman.sendNotification("Dilly"); // 0.5s

      log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(
          beer, // 1s
          vodka); // 0s
   }
}
