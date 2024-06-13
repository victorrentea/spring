package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BarApi {
   private final BarmanService barmanService;
   private final ThreadPoolTaskExecutor poolBar;

   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      // NICIODATA sa nu executi cod cu CompletableFuture in spring fara
      // sa pasezi argumentul 2 (Executor);
      // orice threaduri pornesti sa pornesti pe un thread pool injectat de spring
      var beerPromise = supplyAsync(
          ()->barmanService.pourBeer(), poolBar);
      var vodkaPromise = supplyAsync(
          ()->barmanService.pourVodka(),poolBar);
      log.debug("Mi-a luat comanda");
      Beer beer = beerPromise.get(); // http asteapta 1 sec
      Vodka vodka = vodkaPromise.get(); // http asteapta 0, ca vodka e deja gata cand berea e gata

      // fire-and-forget
      // niciodata nu face runAsync, ci fa metoda @Async
//      runAsync(()->barmanService.auditCocktail("Dilly"), poolBar); // 0.5 sec
        barmanService.auditCocktail("Dilly"); // 0.5 sec
      // VALEU daca cade curentu pana apuc sa auditez, si sunt inca in coada din memorie -> pierd apelul.
      // 1) Coada Rabbit/Kafka/ActiveMQ/Redis/ActiveMQ
      // 2) INSERT taskRepo.save(new Task("auditCocktail", "Dilly"));
         //   +  @Scheduled(fixedRate = 1000) public void polling() {

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }
}
