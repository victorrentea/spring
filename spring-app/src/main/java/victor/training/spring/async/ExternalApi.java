package victor.training.spring.async;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.varie.Sleep;

@RestController
public class ExternalApi { // pretend it's a different app
  @GetMapping("/api/beer")
  public Beer beer() {
    Sleep.millis(1000);
    return new Beer();
  }

  @GetMapping("/api/vodka")
  public Vodka vodka() {
    Sleep.millis(1000);
    return new Vodka();
  }
}
