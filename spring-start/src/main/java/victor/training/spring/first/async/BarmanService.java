package victor.training.spring.first.async;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import victor.training.spring.first.async.drinks.Beer;
import victor.training.spring.first.async.drinks.Vodka;

@Slf4j
@Service
@Timed
@RequiredArgsConstructor
public class BarmanService {
//   private final RestTemplate restTemplate;
//   private final DrinksFeignClient drinksFeignClient;

  @SneakyThrows
  public Beer pourBeer() {
    // imagine api call
    Thread.sleep(1000);
    return new Beer();
//      log.debug("Pouring Beer (SOAP CALL)...");
//      // #1 traditional
//      return restTemplate.getForObject("http://localhost:8080/api/beer", Beer.class);
    // #2 Feign
    // return drinksFeignClient.getBeer();
    // #3 WebClient
    // return webClient.uri(..).get() ...;
  }

  @SneakyThrows
  public Vodka pourVodka() {
    log.debug("Pouring Vodka (REST CALL)...");
    Thread.sleep(1000);
    return new Vodka();
//      return restTemplate.getForObject("http://localhost:8080/api/vodka", Vodka.class);
  }

  @SneakyThrows
  public void auditCocktail(String name) {
    log.info("Longer running task I don't want to wait for: auditing drink: " + name);
    Thread.sleep(500); // pretend send emails or import/export a file
    log.info("DONE");
    if (true) throw new RuntimeException("I don't like Dilly");
    log.info("AFTER");
  }
}
