package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;
   @Autowired
   private ThreadPoolTaskExecutor bar;

   // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Build a non-blocking web endpoint
   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order to " + barman.getClass());

      long t0 = currentTimeMillis();

      // = proxy magic merge pe bar threadpool
      Future<Beer> futureBeer = barman.pourBeer();

      // mai evident ca Pleaca pe alt thread.
      // ATENTIE: daca nu-i pui al doilea param (executoru),
      //    taskul iti ruleaza in ForkJoinPool.commonPool, in care nu ai voie network!
      // atlfel te bati cu orice alt supplyAsync de prin cod si cu toate .parallelStream() din JVM pe doar 7 threaduri.
      Future<Vodka> futureVodka = supplyAsync(() -> barman.pourVodka(), /*OBLIGATORIU*/ bar);

      log.debug("Aici a plecat chelnerul cu AMBELE COMENZI odata");

      Beer beer = futureBeer.get(); // cat timp sta aici blocat threadul Tomcatului ? -> 1 sec
      Vodka vodka = futureVodka.get(); //cat timp sta aici blocat -> 0s: vodka deja e turnata

      long t1 = currentTimeMillis(); // TODO @Timed
      log.debug("Got my drinks in {} millis", t1-t0);

      barman.injura("*R&!*(%^!&(^%*("); // cand chemi o met @Async, ea va executa pe ALT THREAD !!
      log.debug("In patuc");

      return new DillyDilly(beer, vodka);
   }
}
