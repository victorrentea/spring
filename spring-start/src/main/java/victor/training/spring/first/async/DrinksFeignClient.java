package victor.training.spring.first.async;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import victor.training.spring.first.async.drinks.Beer;

@FeignClient("drinks")
public interface DrinksFeignClient {
  @GetMapping("api/beer")
  Beer getBeer();
  // can use @PathVariable, @RequestParam, ..
}
