package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.*;

@Slf4j
@Service
public class Barman {
   @Async("beerPool")
   public CompletableFuture<Beer> pourBeer() {
      log.debug("Pouring Beer (SOAP/WSDL CALL)..."); // soap is the reason why one of the inventors of HTTP
      // (Roy Fieldinf) in his elder days did a PhD REST to resuscitate his younger days
      ThreadUtils.sleepq(1000);
      return completedFuture(new Beer());
   }
   @Async("vodkaPool")
   public CompletableFuture<Vodka> pourVodka() {
//      RestTemplate rest = new RestTemplate();
//      rest.getForObject("http://")
//      new AsyncRestTemplate()
//      WebClient
      log.debug("Pouring Vodka (long sql, REST CALL to an old system)...");
      ThreadUtils.sleepq(1000);
      return completedFuture(new Vodka());
   }
}
