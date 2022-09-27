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
      log.debug("Pouring Beer (SOAP/WSDL CALL)..."); // soap is the reason why one of the inventors of HTTP
      // (Roy Fieldinf) in his elder days did a PhD REST to resuscitate his younger days
      ThreadUtils.sleepq(1000);
      return new Beer();
   }
   public Vodka pourVodka() {
      log.debug("Pouring Vodka (long sql, REST CALL to an old system)...");
      ThreadUtils.sleepq(1000);
      return new Vodka();
   }
}
