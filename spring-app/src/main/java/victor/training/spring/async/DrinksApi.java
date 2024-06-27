package victor.training.spring.async;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.varie.Sleep;

@RestController
public class DrinksApi { // pretend it's running in a different app
  @GetMapping("/api/beer/{type}")
  public Beer beer(@PathVariable String type) {
    Sleep.millis(1000);
    return new Beer().setType(type);
  }

  @GetMapping("/api/vodka")
  public Vodka vodka() {
    Sleep.millis(1000);
    return new Vodka();
  }
}
