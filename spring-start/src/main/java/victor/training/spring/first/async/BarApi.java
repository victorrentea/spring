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
  // Mono<DillyDilly> drink() {
  public CompletableFuture<DillyDilly> drink() throws ExecutionException, InterruptedException {
    log.debug("Submitting my order");

    CompletableFuture<Beer> beerPromise = supplyAsync(() -> barmanService.pourBeer());
    CompletableFuture<Vodka> vodkaPromise = supplyAsync(() -> barmanService.pourVodka());
    // at this point, the 2 tasks are already running in parallel
    //!! in reactive chains declaring 2 Mono<> does NOT make them run YET,
    // in reactive programming, you need to subscribe to the chain to start it !!!!

    // ??? (java8+) === promises (js)
//    Beer beer = beerPromise.get();// blocks the original thread (1MB) for 1 sec until the beer is ready
//    Vodka vodka = vodkaPromise.get();
    // blocking is BAD
    // JS: const [beer, vodka] = await Promise.all([beerPromise, vodkaPromise]);
//    beerPromise.thenCombine(vodkaPromise, (beer,vodka) -> new DillyDilly(beer, vodka));
    CompletableFuture<DillyDilly> dillyPromise = beerPromise.thenCombine(vodkaPromise, DillyDilly::new);
    // reactive: mono1.zipWith(mono2, (beer, vodka) -> new DillyDilly(beer, vodka))

    // fire-and-forget
    ; // send to kafka, send an email

    dillyPromise.thenAccept(cocktail -> barmanService.auditCocktail("Dilly: " + cocktail));
    // reactive: monoDilly.doOnNext(cocktail -> barmanService.auditCocktail("Dilly: " + cocktail)).subscribe();

//    return dillyPromise.get();// reqctive: mono.block(); // blocks the original thread (1MB) for 1 sec until the dilly is ready
    return dillyPromise;
  }
  // all of this madness is for NOT BLOCKING my HTTP thread (1/200) to be able to take more requests in the meantime

  //then java 21 virtual threads happened, making all of the above a nonesense!!!!!
  // we don't mind blocking the thread anymore, because it's not a real thread, it's a virtual thread
  // tomcat could have at one momnent 100K concurrent request running at one point
}
