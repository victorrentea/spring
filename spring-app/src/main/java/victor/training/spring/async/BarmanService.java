package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
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

   public Beer pourBeer(String beer) {
      log.debug("Pouring Beer (SOAP CALL)...");
      // #1 traditional
//      return restTemplate.getForObject("http://localhost:8080/api/{drink}", Beer.class, beer);

      // #2 Feign
       return drinksFeignClient.getBeer();

      // #3 sau: folosesti un client java generat!! din OpenAPI/Swagger spec
      // api-ului pe care-l folosesti din codul tau
      //  openapi-generator-maven-plugin
   }

   public Vodka pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");

//      return restTemplate.getForObject("http://localhost:8080/api/vodka", Vodka.class);
      // mai recent
      return RestClient.create().get().uri("http://localhost:8080/api/vodka")
          .retrieve()
          .body(Vodka.class);
   }

   @Async("executor") // numele beanului de tip ThreadPoolTaskExecutor
   public void auditCocktail(String name) {
      log.debug("Longer running task I don't want to wait for: auditing drink: " + name);
      Sleep.millis(500); // pretend send emails or import/export a file
      if (true) throw new RuntimeException("Intentional"); // Spring o logeaza automat
      log.debug("DONE Audit");
      // utilizari practice de metoda @Async void = aka "fire and forget"
      // - importuri de fisiere uploadate
      // - generari de rapoarte ce sunt trimise pe mail cand sunt gata/downloadate ulterior
      // - trimitere de notificari push/emailuri multe
   }
}
