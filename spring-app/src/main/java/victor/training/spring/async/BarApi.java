package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.DillyDilly;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

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
//
      // Unele lucruri se stocheaza pe threadul curent:
      // - MDC.put("userId","jdoe"); folosit apoi in log pattern
      // - SecurityContextHolder -> @Secured,@RolesAllowed
      // - traceID
      // - @Trasactional/JDBC Connection(s)
      // echivalentul pe Reactor (WebClient) este Reactor Context
/**/
//      WebClient......doOnNext(->)
      // Sfat: WebClient....block() AS SOON AS POSSIBLE.
      // A) pasi independenti in paralel, foloseste CompletableFuture, NU @Async
      var  futureBeer =
          supplyAsync(barman::pourBeer, poolBar); // pasezi mereu un Thread Pool manageuit de SPring
      var futureVodka =
          supplyAsync(barman::pourVodka, poolBar);

      // B) fire-and-forget -> foloseste @Async
      barman.sendNotification("Dilly");

      log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(futureBeer.get(), futureVodka.get());
   }
}
