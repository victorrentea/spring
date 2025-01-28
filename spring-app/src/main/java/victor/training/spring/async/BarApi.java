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
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BarApi {
   private final Barman barman;

   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      // pasi independenti in paralel, foloseste CompletableFuture, NU @Async
      var  futureBeer = supplyAsync(barman::pourBeer);
      var futureVodka = supplyAsync(barman::pourVodka);

      // fire-and-forget -> foloseste @Async
      barman.sendNotification("Dilly");

      log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(futureBeer.get(), futureVodka.get());
   }
}
