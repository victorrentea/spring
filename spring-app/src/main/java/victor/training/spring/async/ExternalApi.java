package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.varie.Sleep;
@Slf4j
@RestController
public class ExternalApi { // pretend it's a different app
  @GetMapping("/api/beer")
  public Beer beer() {
    log.info("Pouring beer by External System");
    Sleep.millis(1000);
    return new Beer();
  }

  @GetMapping("/api/vodka")
  public Vodka vodka() {
    Sleep.millis(1000);
    return new Vodka();
  }
}
