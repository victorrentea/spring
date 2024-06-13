package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BarApi {
   private final BarmanService barmanService;
   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      var beerPromise = supplyAsync(()->barmanService.pourBeer());
      var vodkaPromise = supplyAsync(()->barmanService.pourVodka());
      log.debug("Mi-a luat comanda");
      Beer beer = beerPromise.get(); // http asteapta 1 sec
      Vodka vodka = vodkaPromise.get(); // http asteapta 0, ca vodka e deja gata cand berea e gata

      // fire-and-forget
      runAsync(()->barmanService.auditCocktail("Dilly")); // 0.5 sec

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
