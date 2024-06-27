package victor.training.spring.async;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

@FeignClient("drinksApi") // toate proiectele noi care cheama multe api-uri
public interface DrinksFeignClient { // similar cu Spring Data Repo
  @GetMapping("api/beer")
  Beer getBeer();
  @GetMapping("api/vodka")
  Vodka getVodka();
  // can use @PathVariable, @RequestParam, ..
}
