package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.*;

@Slf4j
@Service
@Timed
public class Barman {
   private final RestTemplate restTemplate;

   public Barman(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   //   @Secured(SOME_ROLE)
   @Async("barPool")
   public CompletableFuture<Beer> pourBeer() {
      log.debug("Pouring Beer (SOAP CALL)...");
      ThreadUtils.sleepMillis(1000);
//      Beer beer = restTemplate.getForObject("url");// blocks for some time the thread
      // Two options:
      // 1) WebClient (spring-boot-starter-webflux instead of spring-boot-starter-web)
      //    able to do network call without blocking ANY thread.
            // also reactive Database Drivers (R2DBC, Mongo, Reactive Rabbit?)
      // 2) wait for Sep 2023 (+6m contingency), when Java 21 LTS is out with Virtual Threads (project Loom) is production ready
      // + Spring Boot 3
      // and keep using BLOCKING good old RestTemplate and JDBC/Momnog driver
      return completedFuture(new Beer());
   }
   @Async("barPool")
   public CompletableFuture<Vodka> pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepMillis(1000);
      return completedFuture(new Vodka());
   }

   @Async("barPool") // tells spring to run it on a different thread from the 'barPool' executor
   public void closeFiscalDay() { // fire-and-forget
      ThreadUtils.sleepMillis(3000);
      log.debug("End of long processing");
      if(true) throw new IllegalArgumentException();
      // exception automatically logged by spring if @Async void method;
   }
}
