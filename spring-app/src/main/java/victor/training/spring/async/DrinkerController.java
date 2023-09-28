package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;

   // TODO [1] autowire and submit tasks to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Make this endpoint non-blocking
   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      CompletableFuture<Beer> beerPromise = supplyAsync(() -> barman.pourBeer());
      CompletableFuture<Vodka> vodkaPromise = supplyAsync(() -> barman.pourVodka());

      // pe CF nu prea da bine faci .get
      Beer beer = beerPromise.get(); // arunca exceptia aparuta in pourBear
      Vodka vodka = vodkaPromise.get();

      CompletableFuture.runAsync(() -> barman.auditCocktail("Dilly")); // aici inca 0.0s -> Fire-and-forget
      // requestul clientul nu mai asteapta sa se faca auditul
      // ERORILE!? trebuie sa fie returnate clientului? NU. doar logate
      // caz mai real: procesarea fisierului uploadat -> trebuie cumva sa raportezi statusul (poate si progresul %)
      //          Cum anunt userul ca fisieru nu poate fi importat?!
      //       a) email : vai de mine n-a mers
      //       b) server-push in interfata sa-l anunt in webpage "gata"/"errori"
      //       c) ii fac pagina dedicata in webapp unde vede statusul uploadurilor. cu erori, ...3w

      // TODO daca nu mai e bere ?!! Andreea

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
