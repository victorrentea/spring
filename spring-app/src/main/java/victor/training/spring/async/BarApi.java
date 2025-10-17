package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
  private final ThreadPoolTaskExecutor poolBar;

  @GetMapping("api/drink")
  public DillyDilly drink() throws Exception {
    log.debug("Submitting my order");
    long t0 = currentTimeMillis();

    CompletableFuture<Beer> beerFuture = supplyAsync(() -> barman.pourBeer(), poolBar); // 1s
    Vodka vodka = barman.pourVodka(); // 1s
    Beer beer = beerFuture.get();// blocks the black = 0s

    barman.sendNotification("Dilly"); // 0.5s // takes time and can fail

    log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
    return new DillyDilly(beer, vodka);
  }
}
