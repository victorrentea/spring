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
import java.util.concurrent.ExecutionException;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;
   @Autowired
   private ThreadPoolTaskExecutor barPool;
   //   @Value("${}")
//   ExecutorService barPool = Executors.newFixedThreadPool(20);

   // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Build a non-blocking web endpoint
   @GetMapping("api/drink")
   public CompletableFuture<DillyDilly> drink() throws ExecutionException, InterruptedException {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();


      System.out.println("Oare cum e posibil? eu chem o functie, si aia nu ruleaza ?");
      System.out.println("Oare barmanul cine este ? " + barman.getClass());
      CompletableFuture<Beer> futureBeer = barman.pourBeer();
      CompletableFuture<Vodka> futureVodka = barman.pourVodka();

//      Beer beer = futureBeer.get(); // < aici tocmai a murit 0.5% (1 din max 200) din Tomcat pt 1 secunda
//      Vodka vodka = futureVodka.get();

      CompletableFuture<DillyDilly> futureDilly =
              futureBeer.thenCombine(futureVodka,
                      (beer, vodka) -> new DillyDilly(beer, vodka));

      //      barman.longRunProcessingFoolowingARESTCALl("mama tata #!*$&%!#&%^&*!#^%&*!^");
      log.info("Ajung in patuc");
      long t1 = currentTimeMillis();
      log.debug("Got my drinks in {} millis", t1-t0);
      return futureDilly;
   }
//      futureDilly.thenAccept(dilly -> httpRespon.write(dilly))
}

//class Acu10AniAIesitServlet3_0 {
//   @GetMapping
//   public void method(HttpServletRequest request) {
//      AsyncContext asyncContext = request.startAsync();
//
//      // din alt thread
////      {
////         asyncContext.getResponse().getWriter().write(dilly);
////         asyncContext.complete(); // aici ii spui lui Tomcat sa inchida conn TCP IP
////      }
//   }
//}
