package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

@Slf4j
@Service
@Timed
public class Barman {
//   @Async // by default mege pe asyncExecutor standard din spring
//   public CompletableFuture<Beer> pourBeer() {
//      if (true) throw new IllegalArgumentException("nu mai e bere");
   public Beer pourBeer() {
      log.debug("Pouring Beer (SOAP CALL)...");
      ThreadUtils.sleepMillis(1000);
      return new Beer();
   }
//   @Async("barPool")
//   public CompletableFuture<Vodka> pourVodka() {
   public Vodka pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepMillis(1000);
      return new Vodka();
   }


   // foarte sanatos pentru procesari lasate in background pornite din HTTP
   @Async
   public void fireAndForget(String date) {
      //chestii grele minute, ore
      throw new IllegalArgumentException("Vreo exceptie e pusa in log automat" );
   }
}
