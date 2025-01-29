package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.varie.Sleep;

import static java.lang.System.currentTimeMillis;

@Slf4j
@Service
@Timed
@RequiredArgsConstructor
public class Barman {
  private final RestTemplate restTemplate;
  private final DrinksFeignClient drinksFeignClient;

  @Timed("pour-beer-pt-alex")
//  @Secured("ROLE_SEFU")
  public Beer pourBeer() {
    log.debug("Fetching Beer...");
    // #1 traditional
    String type = "blond";
    return restTemplate.getForObject("http://localhost:8080/api/beer/{type}", Beer.class, type);

    // #2 Feign
    // return drinksFeignClient.getBeer(type);
  }

  public Vodka pourVodka() {
    log.debug("Fetching Vodka...");
    return restTemplate.getForObject("http://localhost:8080/api/vodka", Vodka.class);

    // #3 RestClient
    // return restClient.uri(..).get() ...;

    // #4 generated client from open-api/swagger 💖
  }
  @Async("notificationPool")
  public void sendNotification(String email) { // TODO outbox pattern
    log.debug("Sending notification (takes time and might fail) {}...", email);
    Sleep.millis(500); // critical but slow work that can fail
    if (Math.random() < 0.5) throw new RuntimeException("🔔 Email server down");
    log.debug("Notification sent!");
    // Ganduri:
    // - crapa in background, cum ma prind?
    // - se blocheaza, nici nu sare eroare in log.
    // - race condition (atacul secretarei colerice~>)
    // - iti da k8s kill/redeploy TU > si tu nu ai testa daca podu isi termina treaba pana moare
    // - pe cate threaduri ruleaza asta?
  }

}
