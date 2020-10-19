package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.ThreadUtils;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class Barman {
   @Async("barmanEx")
   public CompletableFuture<Beer> getOneBeer() {
      log.debug("Pouring Beer...");
      ThreadUtils.sleep(1000);// expensive Webservice call
      return CompletableFuture.completedFuture(new Beer());
   }

   @Async("barmanEx")
   public CompletableFuture<Vodka> getOneVodka() {
      log.debug("Pouring Vodka...");
      ThreadUtils.sleep(1000); // DB query/ citiri de fis/ encrypturi nasoale
      return CompletableFuture.completedFuture(new Vodka());
   }
}
