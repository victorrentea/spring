package victor.training.spring.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import victor.training.spring.ThreadUtils;

public class DillyDilly {
   private static final Logger log = LoggerFactory.getLogger(DillyDilly.class);
   private final Beer beer;
   private final Vodka vodka;


   public DillyDilly(Beer beer, Vodka vodka) {
      this.beer = beer;
      this.vodka = vodka;
      log.info("Amestec beuturi");
      ThreadUtils.sleep(1000);

   }

   public Beer getBeer() {
      return beer;
   }


   public Vodka getVodka() {
      return vodka;
   }

   @Override
   public String toString() {
      return "DillyDilly{" +
             "beer=" + beer +
             ", vodka=" + vodka +
             '}';
   }
}
