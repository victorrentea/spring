package victor.training.spring.async;

import io.swagger.v3.oas.annotations.Operation;
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
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BarApi {
  private final Barman barman;
  private final ThreadPoolTaskExecutor executor;

  @Operation(description = "Sub 1 sec")
  @GetMapping("api/drink")
  public DillyDilly drink() throws Exception {
    log.debug("Submitting my order");
    long t0 = currentTimeMillis();

    // linia urmatoare NU trebuie sa apara in cod Spring!
//    CompletableFuture<Vodka> futureVodka = supplyAsync(barman::pourVodka); // NICIODATA
    // 1) pierzi TraceID
    // 2) competitionezi cu oricine lucreaza pe ForkJoinPool.commonPool() => orice .parallelStream si orice alt CompletableFuture.
    // 3) nu are max queue size
    CompletableFuture<Vodka> futureVodka = supplyAsync(barman::pourVodka, executor); // DA

    Beer beer = barman.pourBeer();

    Vodka vodka = futureVodka.get();

    barman.sendNotification("Dilly"); // ---- 0.5s FIRE-AND-FORGET pattern: nu-mi pasa de erori, nu vreau sa-l astept

    log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
    return new DillyDilly(beer, vodka);
  }
}
