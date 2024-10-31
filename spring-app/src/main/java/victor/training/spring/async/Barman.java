package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.varie.Sleep;

@Slf4j
@Service
@Timed
@RequiredArgsConstructor
public class Barman {
//  @Qualifier("restClientPtBauturi")
  private final RestClient restClientPtBauturi;
  private final RestTemplate restTemplate;
  private final DrinksFeignClient drinksFeignClient;

  public Beer pourBeer() {
    log.debug("Fetching Beer...");
    // #1 traditional
    String type = "blond";
//    return restTemplate.getForObject(
//    "http://localhost:8080/api/beer/{type}",
//        Beer.class, type);

    // #2 Feign
     return drinksFeignClient.getBeer(type);
  }

  public Vodka pourVodka() {
    log.debug("Fetching Vodka...");
//    return restTemplate.getForObject("http://localhost:8080/api/vodka",
//    Vodka.class);

    // #3 RestClient
    // #4 sau WebClient pe web-flux
   return restClientPtBauturi
       .get()
       .uri("http://localhost:8080/api/vodka")
       .retrieve()
       .body(Vodka.class);


    // #4 generated client from open-api/swagger 💖
  }

  public void auditCocktail(String name) { // TODO fire-and-forget
    log.debug("Longer running task I don't want to wait for: auditing drink: {}", name);
    Sleep.millis(500); // non-critical work
    log.debug("DONE Audit");
  }

  @Async
  public void sendEmail(String email) { // TODO outbox pattern
    log.debug("Sending report {}...", email);
    Sleep.millis(500); // critical but slow work that can fail
    if (Math.random() < 0.5) throw new RuntimeException("Email server down");
    log.debug("Email sent!");
  }

}
