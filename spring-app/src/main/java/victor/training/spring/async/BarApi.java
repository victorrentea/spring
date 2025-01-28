package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
      // pasi independenti in paralel, foloseste CompletableFuture, NU @Async
      var  futureBeer =
          supplyAsync(barman::pourBeer, executor);
      var futureVodka =
          supplyAsync(barman::pourVodka, executor);

      // fire-and-forget -> foloseste @Async
      barman.sendNotification("Dilly");

      log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(futureBeer.get(), futureVodka.get());
   }
}
