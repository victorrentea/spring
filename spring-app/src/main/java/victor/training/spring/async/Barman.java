package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

@Slf4j
@Service
public class Barman {
   public Beer pourBeer() {
//      if (true) {
//         throw new IllegalArgumentException("Not more beer!");
//      }
      log.debug("Pouring Beer (SOAP CALL)...");
      ThreadUtils.sleepq(1000);
      return new Beer();
   }
   public Vodka pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepq(1000);
      return new Vodka();
   }

   public void curse(String curse) {
      if (curse!=null)
         throw new RuntimeException("kill ya");
   }
}
