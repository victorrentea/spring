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
import static java.util.concurrent.CompletableFuture.supplyAsync;

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

      CompletableFuture<Beer> futureBeer = supplyAsync(() -> barman.pourBeer(), executor);
      CompletableFuture<Vodka> futureVodka = supplyAsync(() -> barman.pourVodka(), executor);

//      Beer beer = futureBeer.get(); // the Tomcat threads have to wait 1 second here
//      Vodka vodka = futureVodka.get(); // no wait, the vodka was already poured
      // if 200 parallel requests hit this endpoint, Tomcat will be left out of threads for 1 sec.

      CompletableFuture<DillyDilly> f = futureBeer.thenCombine(futureVodka, (b, v) -> new DillyDilly(b, v));
      long t1 = currentTimeMillis();
      log.debug("Method returns in  {} millis", t1-t0);
//      f.thenAccept(dilly-> jackson asyncContext.write)
      return f;
//            .thenAccept(dillyDilly -> log.debug("Got my drinks in {} millis", currentTimeMillis()-t0));

//      return new DillyDilly(beer, vodka);
   }


   public void method(HttpServletRequest request) {
      AsyncContext asyncContext = request.startAsync();


   }
   // in a different thread
//      asyncContext.getResponse().getWriter().write(jackson.toStrin("data");
//      asyncContext.complete();
}
