package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class DrinksClient {
    private final RestTemplate restTemplate;
    private final RestClient restClient;
    private final DrinksApiFeignClient drinksApiFeignClient;

    public Beer pourBeer() {
        String type = "blond";
        log.debug("Calling GET Beer...");
        // #1 RestTemplate (traditional)
        return restTemplate.getForObject("http://localhost:8080/api/beer/{type}", Beer.class, type);

        // #2 Feign Client 😎
//     return drinksFeignClient.getBeer(type);
    }

    public Vodka pourVodka() {
        log.debug("Calling GET Vodka...");
//    return restTemplate.getForObject("http://localhost:8080/api/vodka", Vodka.class);

        // #3 RestClient ⭐️ = blocking equivalent of WebClient☠️
        return restClient.get()
                .uri("http://localhost:8080/api/vodka")
                .retrieve()
                .body(Vodka.class);

        // #4 generated client from open-api/swagger 💖
    }

    public void sendNotification(String email) { // TODO outbox pattern
        log.debug("Sending notification (takes time and might fail) {}...", email);
        Sleep.millis(500); // critical but slow work that can fail
        if (Math.random() < 0.5) throw new RuntimeException("Email server down💥");
        log.debug("Notification sent!");
    }

}
