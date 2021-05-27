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
import victor.training.spring.ThreadUtils;

import java.util.concurrent.CompletableFuture;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
   public static void main(String[] args) {
      new SpringApplicationBuilder(AsyncApp.class)
          .profiles("spa") // re-enables WEB nature (disabled in application.properties for the other apps)
          .run(args);
   }

   @Bean
   public ThreadPoolTaskExecutor beerPool(@Value("${beer.count}") int barmanCount) {
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
   public ThreadPoolTaskExecutor vodkaPool(@Value("${vodka.count}") int barmanCount) {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(barmanCount);
      executor.setMaxPoolSize(barmanCount);
      executor.setQueueCapacity(500);
//		executor.setRejectedExecutionHandler(new );
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

//   @Autowired
//   private ThreadPoolTaskExecutor executor;

   // TODO [1] inject and use a ThreadPoolTaskExecutor.submit
   // TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
   // TODO [3] Messaging...
   @GetMapping
   public CompletableFuture<DillyDilly> method() {
      log.debug("Submitting my order " + barman.getClass());

      CompletableFuture<Beer> futureBeer = barman.getOneBeer();
      CompletableFuture<Vodka> futureVodka = barman.getOneVodka();
      log.debug("a plecat baiatu' cu comanda");

//		futureBeer.thenApply(beer -> puneGheata(bere))

//		q.all(p1,p2, function(d1,d2) { } )

      CompletableFuture<DillyDilly> futureDilly = futureBeer.thenCombineAsync(futureVodka, (b, v) -> new DillyDilly(b, v));

      futureDilly.thenAccept(dilly -> log.debug("Got my order! Thank you lad! " + dilly));

      barman.injur("$@#$!%$^!&$^@!&$^!&@$^");

      log.debug("plec.");
      return futureDilly;

//		ThreadUtils.sleep(3000);
   }
}

@Data
class DillyDilly {
   private final Beer beer;
   private final Vodka vodka;

}

//vinu dupa bere e placere
//bere dupa vin e un chin

@Slf4j
@Service
class Barman {
   @Async("beerPool")
   public CompletableFuture<Beer> getOneBeer() {
      log.debug("Pouring Beer...");
//      if (true) {
//         throw new IllegalStateException("Nu mai e bere");
//      }

      ThreadUtils.sleep(1000); // external REST call
      log.debug("Pouring Beer Complete...");
      return CompletableFuture.completedFuture(new Beer());
   }

   @Async("vodkaPool")
   public CompletableFuture<Vodka> getOneVodka() {
      log.debug("Pouring Vodka...");
      ThreadUtils.sleep(1000); // DB call
      return CompletableFuture.completedFuture(new Vodka());
   }

   @Async
   public void injur(String uratura) {
      if (uratura != null) {
         throw new RuntimeException("Iti fac buzunar");
      }
   }
}

@Data
class Beer {
   public final String type = "blond";
}

@Data
class Vodka {
   public final String type = "deadly";
}
