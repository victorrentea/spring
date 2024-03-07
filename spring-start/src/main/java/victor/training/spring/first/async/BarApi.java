package victor.training.spring.first.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.async.drinks.Beer;
import victor.training.spring.first.async.drinks.DillyDilly;
import victor.training.spring.first.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
public class BarApi {
  @Autowired
  private BarmanService barmanService;

  // TODO [1] autowire and submit tasks to a ThreadPoolTaskExecutor
  // TODO [2] mark pour* methods as @Async
  // TODO [3] Make this endpoint non-blocking
  @GetMapping("api/drink")
  public DillyDilly drink() throws ExecutionException, InterruptedException {
    log.debug("Submitting my order");
    long t0 = currentTimeMillis();

    CompletableFuture<Beer> beerPromise = supplyAsync(() -> barmanService.pourBeer());
    CompletableFuture<Vodka> vodkaPromise = supplyAsync(() -> barmanService.pourVodka());
    // at this point, the 2 tasks are already running in parallel
    //!! in reactive chains declaring 2 Mono<> does NOT make them run YET,
    // in reactive programming, you need to subscribe to the chain to start it !!!!

    // ??? (java8+) === promises (js)
    Beer beer = beerPromise.get();// blocks the original thread (1MB) for 1 sec until the beer is ready
    Vodka vodka = vodkaPromise.get();
    // blocking is BAD

    DillyDilly cocktail = new DillyDilly(beer, vodka);
    // fire-and-forget
    barmanService.auditCocktail("Dilly: " + cocktail); // send to kafka, send an email

    log.debug("Method completed in {} millis", currentTimeMillis() - t0);
    return cocktail;
  }
}
