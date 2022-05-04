package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import victor.training.spring.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

@Slf4j
@Service
public class Barman {
   public Beer pourBeer() {
      log.debug("Pouring Beer (SOAP CALL)...");
      ThreadUtils.sleepq(1000);
      return new Beer();
   }
   public Vodka pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepq(1000);
      return new Vodka();
   }
}
