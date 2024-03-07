package victor.training.spring.first.async;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.async.drinks.Beer;
import victor.training.spring.first.async.drinks.Vodka;

@RestController
public class ExternalApi { // pretend it's a different app
  @SneakyThrows
  @GetMapping("/api/beer")
  public Beer beer() {
    Thread.sleep(1000);
    return new Beer();
  }

  @SneakyThrows
  @GetMapping("/api/vodka")
  public Vodka vodka() {
    Thread.sleep(1000);
    return new Vodka();
  }
}
