package victor.training.spring.async.drinks;

import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class DillyDilly {
   Beer beer;
   Vodka vodka;

   public DillyDilly(Beer beer, Vodka vodka) {
      log.debug("oare pe ce thread scriu rasp la client inaopi?");
      this.beer = beer;
      this.vodka = vodka;
   }
}
