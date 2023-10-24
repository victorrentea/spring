package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

@Slf4j
@Service
@Timed
public class Barman {
   public Beer pourBeer() {
      log.debug("Pouring Beer (SOAP CALL)...");
      ThreadUtils.sleepMillis(1000); // imagine a RestTemplate.get...
      return new Beer();
   }
   public Vodka pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepMillis(1000);
      return new Vodka();
   }

   @Async("executor") // name of the thread pool bean to run on
   public void auditCocktail(String name) {
      log.debug("Longer running task I don't need to wait for using data: " + name);
      ThreadUtils.sleepMillis(500);
      log.debug("DONE");
      throw new IllegalArgumentException("OUPS. BIG BAD ERROR");
   }
}
