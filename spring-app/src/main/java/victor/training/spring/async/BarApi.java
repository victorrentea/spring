package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.DillyDilly;

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

    var futureBeer =
        CompletableFuture.supplyAsync(() -> barman.pourBeer());
    var futureVodka =
        CompletableFuture.supplyAsync(() -> barman.pourVodka());
    var beer = futureBeer.get(); // blocheaza 1s
    var vodka = futureVodka.get(); // blocheaza 0s

    // fire-and-forget task dupa care NU astept, care poate crapa in background
    CompletableFuture.runAsync(() -> {
      barman.sendNotification("Dilly"); // .5s
    });

    log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
    return new DillyDilly(beer, vodka);
  }
}
