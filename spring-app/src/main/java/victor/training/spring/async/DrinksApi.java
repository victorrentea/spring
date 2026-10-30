package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.varie.Sleep;

@Slf4j
@RestController
public class DrinksApi { // pretend it's running in a different app
  @GetMapping("/api/beer/{type}")
  public Beer beer(@PathVariable String type) {
    log.info("Remote service: pouring beer");
    Sleep.millis(1000);
    return new Beer().type(type);
  }

  @GetMapping("/api/vodka")
  public Vodka vodka() {
    log.info("Remote service: pouring vodka");
    Sleep.millis(1000);
    if (Math.random() < 0.75) {
      throw new IllegalStateException("No vodka left!");
    }
    return new Vodka();
  }
}
