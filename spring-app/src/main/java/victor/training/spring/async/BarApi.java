package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
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
  private final DrinksClient drinksClient;

  @GetMapping("api/drink")
  @Timed // = quarkus
  public DillyDilly drink() throws Exception {
    log.debug("Submitting my order");

    var beerPromise = supplyAsync(drinksClient::pourBeer);
    var vodkaPromise = supplyAsync(drinksClient::pourVodka);
    // exista 3 threaduri: Tomcat + 2 din ForkJoinPool.commonPool()
    // ⚠️ e mic th poolul default (NCPU-1) (victor:9) nu e usor configurabil
    // ⚠️ pierzi trace id
    // ⚠️ competitionezi unfair cu .parallelStream()
    Beer beer = beerPromise.get(); // 1s 🥺
    Vodka vodka = vodkaPromise.get(); // 0s

    drinksClient.sendNotification("Dilly"); //-0.5s = 0s
    log.debug("HTTP thread released");
    return new DillyDilly(beer, vodka);
  }
}
