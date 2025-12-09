package victor.training.spring.async;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

@FeignClient("drinks")
public interface DrinksApiFeignClient {
    @GetMapping("api/beer/{type}")
    Beer getBeer(@PathVariable("type") String type);

    @GetMapping("api/vodka")
    Vodka getVodka();
}
