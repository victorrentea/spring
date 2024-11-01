package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.repo.TeacherRepo;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BarApi {
   private final Barman barman;
   private final ThreadPoolTaskExecutor poolBar;

   @GetMapping("accept-payment")
    public void acceptPayment() { // va da 503 daca sunt sute de req in curs pe /api/drink
      // = unfairness
        log.info("Payment accepted");
    }

   @GetMapping("api/drink")
   public CompletableFuture<DillyDilly> drink() throws Exception {
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

      CompletableFuture<DillyDilly> futureDilly = futureBeer
          .thenCombine(futureVodka, (b, v) -> new DillyDilly(b, v));

      barman.auditCocktail("Dilly");
      barman.sendEmail("Reporting Dilly");
      log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
      return futureDilly; // spring va face .thenAccept si va scrie pe response HTTP cand e gata dilly.
   }

   private void altaMetodaChemaInAcelasiThread() {
      log.info("I'm doing something else in the same thread");
      teacherRepo.findByContractType(ContractType.INDEPENDENT);
   }
   private final TeacherRepo teacherRepo;
}
