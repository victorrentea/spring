package victor.training.spring.async;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Slf4j
@Service
@Timed
public class Barman {
   @Async("executor") // name of the bean ThreadPoolTE
   public CompletableFuture<Beer> pourBeer() {
      log.debug("Pouring Beer (SOAP CALL)...");
      // on any REST/grpc/MQ send, the TraceId on the current thread
      // will be sent along via a HEADER; the next system will pick
      // it up from the header and SET IT ON ITS THREAD

      // we are still blocking a thread, wasting resources because the way we called the APIS/NETWORK is still blocking in nature.
      // to fix that -> Reactive chains using Drivers/WebClient
//      restTemplate.getForObject()
//      webClient......block()
      ThreadUtils.sleepMillis(1000);
      return completedFuture(new Beer());
   }
   @Async("executor")
   public CompletableFuture<Vodka> pourVodka() {
      log.debug("Pouring Vodka (REST CALL)...");
      ThreadUtils.sleepMillis(1000);
      return completedFuture(new Vodka());
   }

   @Async("executor")// + void return makes spring log the error automatically
   public void auditCocktail(String name) {
      log.debug("Longer running task I don't need to wait for using data: " + name);
      ThreadUtils.sleepMillis(500);
      if (true) {
         throw new IllegalArgumentException("OUPS!!");
      }
      log.debug("DONE");
   }

//   @Bulkhead()
   @Async("longsql") // throttling using a small-sized thread pool, wasting threads for a separate thread pool
   public CompletableFuture<String> fatPig() {
      // I want to restrict this method to max 2 calls in parallel
      int r = new Random().nextInt();
      log.info("START " + r);
      ThreadUtils.sleepMillis(1000);
      log.info("END " + r);
      return CompletableFuture.completedFuture("data");
   }

   // like a SEMAPHORE from Faculty
   @Bulkhead(name = "longsql") // no extra threads, but could STARVE tomcat threadpool
   public String fatPigBulkhead() {
      // I want to restrict this method to max 2 calls in parallel
      int r = new Random().nextInt();
      log.info("START " + r);
      ThreadUtils.sleepMillis(1000);
      log.info("END " + r);
      return "data";
   }
}
