package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;
import victor.training.spring.ThreadUtils;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.*;

@EnableAsync
@SpringBootApplication
public class AsyncApp {

   public static void main(String[] args) {
      new SpringApplicationBuilder(AsyncApp.class)
          .profiles("spa")
          .run(args);
   }

   @Bean
   public ThreadPoolTaskExecutor beerPool(@Value("${beer.count:5}") int barmanCount) {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(barmanCount);
      executor.setMaxPoolSize(barmanCount);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("beer-");
      executor.initialize();
      executor.setWaitForTasksToCompleteOnShutdown(true);
      return executor;
   }
   @Bean
   public ThreadPoolTaskExecutor vodkaPool(@Value("${vodka.count:20}") int barmanCount) {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(barmanCount);
      executor.setMaxPoolSize(barmanCount);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("vodka-");
      executor.initialize();
      executor.setWaitForTasksToCompleteOnShutdown(true);
      return executor;
   }

}

@Slf4j
@RestController
class Drinker {
   @Autowired
   private Barman barman;

   // TODO [1] inject and use a ThreadPoolTaskExecutor.submit
   // TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
   // TODO [3] Messaging...
   @GetMapping
   public CompletableFuture<DillyDilly> getDrinks() throws ExecutionException, InterruptedException {
      log.debug("Submitting my order to " + barman.getClass());
//		ExecutorService pool = Executors.newFixedThreadPool(2);

      // 1 mangeuit pool cu spring DON
      // 2 @Async DONE
      // 3 Endpoint HTTP asincron --- Reactive Programming: non-blocking request handling

      CompletableFuture<Beer> futureBeer = barman.getOneBeer();
      CompletableFuture<Vodka> futureVodka = barman.getOneVodka();

      log.debug("Aici a plecata fata cu comenzile mele");

      CompletableFuture<DillyDilly> futureDilly = futureBeer.thenCombine(futureVodka, (b, v) -> new DillyDilly(b, v));

      log.debug("Iese threadul de http din metoda. Poate deci servi alti clienti ");

      barman.injura("!!$^!%@^#!@%#!*%!@");

      return futureDilly;
   }
}

@Data
class DillyDilly {
   private final Beer beer;
   private final Vodka vodka;
}

@Slf4j
@Service
class Barman {
   @Async("beerPool")
   public CompletableFuture<Beer> getOneBeer() { // throughput pt max 5 req paralelle - ca face spuma berea
//      if (true) {
//         throw new IllegalArgumentException("Nu mai e bere");
//      }
      log.debug("Pouring Beer...");
      ThreadUtils.sleep(1000); // RMI call
      return CompletableFuture.completedFuture(new Beer());
   }

   @Async("vodkaPool")
   public CompletableFuture<Vodka> getOneVodka() { // throughput pt max 20 req paralelle
      log.debug("Pouring Vodka...");
      ThreadUtils.sleep(1000); // SOAP call
      return CompletableFuture.completedFuture(new Vodka());
   }

   @Async
   public void injura(String uratura) { // fire and forget
      if (uratura != null) {
         throw new IllegalArgumentException("Iti fac buzunar");
      }
   }
}

@Data
class Beer {
   public String type = "blond";
}

@Data
class Vodka {
   public String type = "deadly";
}
