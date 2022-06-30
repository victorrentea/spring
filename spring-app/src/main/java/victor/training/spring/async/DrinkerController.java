package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;
   @Autowired
   private ThreadPoolTaskExecutor executor;

   // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Build a non-blocking web endpoint
   @GetMapping("api/drink")
   public CompletableFuture<DillyDilly> drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      // cand ceri o executie asincrona cu threadpooluri in Java obtii un Future.
      //pe care poti ulterior sa blochezi sa astepti rezultatul
      CompletableFuture<Beer> futureBeer = barman.pourBeer();
      CompletableFuture<Vodka> futureVodka = barman.pourVodka();
      log.debug("A plecat garcon cu comanda");
//      Beer beer = futureBeer.get(); //aici HTTP threadul asteapta 1 sec
//      Vodka vodka = futureVodka.get(); // aici HTTP threadul nu mai asteapta de loc. vodka e deja pregatita.

      CompletableFuture<DillyDilly> futureDilly = futureBeer
              .thenCombineAsync(futureVodka, (b, v) -> new DillyDilly(b, v));

      long t1 = currentTimeMillis();
      log.debug("Got my drinks in {} millis", t1-t0);
      return futureDilly;
   }


//   public void method(HttpServletRequest request) {
//      AsyncContext asyncContext = request.startAsync();
//      // din orice thread
//
//      asyncContext.getResponse().getWriter().write("Response");
//      asyncContext.complete();
//   }
}
