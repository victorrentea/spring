package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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

      altaMetodaChemaInAcelasiThread();
//      Future<Beer> futureBeer = poolBar.submit(barman::pourBeer);
//      Future<Vodka> futureVodka = poolBar.submit(barman::pourVodka);
      // Gafa: sa folosesti CompletableFuture care by default submite pe ForkJoinPool global din JVM
      // acel thread pool JVM nu face thread-hopping de metadata, si
      // - pierzi traceID-ul
      // - pierzi si identitatea userului care a pornit fluxul (SecurityContext)
//      CompletableFuture<Beer> futureBeer = CompletableFuture.supplyAsync(barman::pourBeer);
//      CompletableFuture<Vodka> futureVodka = CompletableFuture.supplyAsync(barman::pourVodka);

      // REGULA in spring daca vrei sa fol COmpletableFuture in orice metoda care se termina in
      // ...Async trebuie sa ii dai si un ThreadPoolTaskExecutor injectat de spring pe care sa execute.
      CompletableFuture<Beer> futureBeer = CompletableFuture.supplyAsync(barman::pourBeer, poolBar);
      CompletableFuture<Vodka> futureVodka = CompletableFuture.supplyAsync(barman::pourVodka, poolBar);

      Beer beer = futureBeer.get();
      Vodka vodka = futureVodka.get();

      barman.auditCocktail("Dilly");
      barman.sendEmail("Reporting Dilly");
      log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }

   private void altaMetodaChemaInAcelasiThread() {
      log.info("I'm doing something else in the same thread");
   }
}
