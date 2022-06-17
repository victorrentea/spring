package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class Barman {
   @Async("barPool")
   public CompletableFuture<Beer> pourBeer() {
//      if (true) {
//         throw new IllegalArgumentException("DRAMA: nu mai ebvere!!!!!!");
//      }
      log.debug("Pouring Beer (SOAP CALL)...");
      ThreadUtils.sleepq(1000);
      return CompletableFuture.completedFuture(new Beer());
   }
   @Async("vodkaPool")
   public CompletableFuture<Vodka> pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");

//      Mono < reactor, webflux. WebClient
//      CompletableFuture<ResponseEntity<Object>> nonBlock =
//              new AsyncRestTemplate().exchange().completable();
      ThreadUtils.sleepq(500);
      return CompletableFuture.completedFuture(new Vodka());
   }

   @Async
   public void injura_FIRE_AND_FORGET(String s) {
      ThreadUtils.sleepq(1000);
      if (s != null) {
         throw new IllegalArgumentException("iti fac buzuna / te casez");
      }
      log.debug("send email");
   }
}
