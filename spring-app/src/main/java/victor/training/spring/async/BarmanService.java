package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.varie.Sleep;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

@Slf4j
@Service
@Timed
@RequiredArgsConstructor
public class BarmanService {
   private final RestTemplate restTemplate;
   private final DrinksFeignClient drinksFeignClient;

   public Beer pourBeer() {
      log.debug("Pouring Beer (SOAP CALL)...");
      // #1 traditional
//      if(true) throw new IllegalStateException();
      return restTemplate.getForObject("http://localhost:8080/api/beer", Beer.class);

      // #2 Feign
      // return drinksFeignClient.getBeer();

      // #3 WebClient
      // return webClient.uri(..).get() ...;
   }

   public Vodka pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      return restTemplate.getForObject("http://localhost:8080/api/vodka", Vodka.class);
   }

   @Async("poolBar") // logeaza orice exceptie sare
   public void auditCocktail(String name) {
      log.debug("Longer running task I don't want to wait for: auditing drink: " + name);
      Sleep.millis(500); // pretend send emails or import/export a file
      if (true)throw new RuntimeException("Incredibil!!. S-a spart audit service-ul");
      log.debug("DONE");
   }
}
