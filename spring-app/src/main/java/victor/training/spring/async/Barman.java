package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

@Slf4j
@Service
@Timed
public class Barman {
//   private String currentUsername; // nu tii pe campurile unui singleton date asoc
//   // requestului curent.
//
//   public void setCurrentUsername(String currentUsername) {
//      this.currentUsername = currentUsername;
//   }

   public Beer pourBeer() {
      log.debug("Pouring Beer (SOAP CALL) for ...");
      ThreadUtils.sleepMillis(1000);
      return new Beer();
   }
   public Vodka pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepMillis(1000);
      return new Vodka();
   }
}
