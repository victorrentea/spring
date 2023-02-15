package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class Barman {
   @Async("barPool") // some HATE it
   public CompletableFuture<Beer> pourBeer() {
      boolean drama = true;
//      if (drama) throw new RuntimeException("OMG no beer!");
      log.debug("Pouring Beer for {} (SOAP CALL)...", ThreadLocalStuff.THREAD_LOCAL_DATA.get());
      ThreadUtils.sleepq(1000);
      return CompletableFuture.completedFuture(new Beer());
   }

   @Async("barPool")
   public CompletableFuture<Vodka> pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepq(1000);
      return CompletableFuture.completedFuture(new Vodka());
   }

   @Async // MANDATORY way of starting fire and forget calls
   public void washGlasses() {
      log.info("Start doing the long task");
      // process a file
      // produce a 1 GB xml
      // send 1000 email
      ThreadUtils.sleepq(5000);
      if(true) throw new RuntimeException("OMG!");
      log.info("End");
   }
}

