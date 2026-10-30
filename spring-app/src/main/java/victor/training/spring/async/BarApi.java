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
  private final DrinksClient drinksClient;
  private final ThreadPoolTaskExecutor poolBar; // its TaskDecorator propagates the trace (ContextSnapshot)

  @GetMapping("api/drink")
  public DillyDilly drink() throws Exception {
    log.debug("Submitting my order");
    long t0 = currentTimeMillis();

    //ambele linii de mai jos dureaza 0 s ⇒ pornesc cate a lămbdica intr-un nou thread
    CompletableFuture<Beer> beerFuture = CompletableFuture.supplyAsync(
            ()->drinksClient.pourBeer(), poolBar); // without an executor ⇒ ForkJoinPool.commonPool, no traceId

    var vodka = drinksClient.pourVodka();
    Beer beer = beerFuture.join();

    drinksClient.sendNotification("Dilly");

    log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
    return new DillyDilly(beer, vodka);
  }
}
