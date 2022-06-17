package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;

   // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Build a non-blocking web endpoint
   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();


      // junioru contrariat: eu cand chem o functie, nu tre sa astept sa se termine ?
      Future<Beer> futureBeer = barman.pourBeer(); // aplul asta NU executa de fapt nimic, doar scheduleaza un task de executat intr-un pool invizibil candva in viitor
      Future<Vodka> futureVodka = barman.pourVodka();

      log.debug("Aici a plecat garcon cu comanda");
      Vodka vodka = futureVodka.get();
      Beer beer = futureBeer.get();

      long t1 = currentTimeMillis();
      log.debug("Got my drinks in {} millis", t1-t0);
      return new DillyDilly(beer, vodka);
   }
}
