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
      ThreadUtils.sleepMillis(1000);
      return new Beer();
   }
   public Vodka pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepMillis(1000);
      return new Vodka();
   }

   @Async("barPool")
   // fire-and-forget: not to wait for it to complete and NOT to fail if it throws
   public void exportBigFile(String s) {
      log.info("Start the exportBigFile: " + s);
      ThreadUtils.sleepMillis(5000);
      log.info("End the exportBigFile: " + s);
//      if (s != null) {
//         throw new IllegalArgumentException("Oups, ");
//      }
   }
}
