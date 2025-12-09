package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.DillyDilly;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import static java.lang.System.currentTimeMillis;

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
    // Probleme
    // 1) sa propagi traceId catre cele 2 threaduri pornite
    // 2) sa controlezi pe cate threaduri executi callurile
    // 3) sa nu competitionezi cu ForkJoinPool.commonPool parallelStream
    var futureBeer =
        CompletableFuture.supplyAsync(() -> barman.pourBeer(), poolBar);
    var vodka = barman.pourVodka();// torni vodka activ 1s
    var beer = futureBeer.get(); // blocheaza 0s e deja turnata de prietenul tau

//    var beer= barman.pourBeer();

    // fire-and-forget task dupa care NU astept, care poate crapa in background
//    CompletableFuture.runAsync(() -> barman.sendNotification("Dilly")); // Exceptiile se pierd
    barman.sendNotification("Dilly"); // ⚠️ trebuie sa stai cu alarme pe log

    log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
    return new DillyDilly(beer, vodka);
  }
}
