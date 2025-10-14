package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.varie.Sleep;

import java.util.concurrent.ScheduledThreadPoolExecutor;

@Slf4j
@Service
@Timed
@RequiredArgsConstructor
public class Barman {
  private final RestTemplate restTemplate;
  private final DrinksFeignClient drinksFeignClient;



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

  @Async // modul recomandat de a face fire-and-forget in background
  public void sendNotification(String email) { // TODO outbox pattern
    log.debug("Sending notification (takes time and might fail) {}...", email);
    Sleep.millis(500); // critical but slow work that can fail
    if (Math.random() < 0.5) throw new RuntimeException("Email server down");
    log.debug("Notification sent!");
  }

}
