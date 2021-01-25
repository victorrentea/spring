package victor.training.spring.async;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.concurrent.*;

import static java.util.concurrent.CompletableFuture.completedFuture;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
   public static void main(String[] args) {
      new SpringApplicationBuilder(AsyncApp.class)
          .profiles("spa")
          .run(args);
   }

//   public ThreadPoolTaskExecutor barmanThreadPool2(@Value("${barman.count}") int barmanThreadCount) {

   @Bean // defined a spring bean named "barmanThreadPool" of type ThreadPoolTaskExceutor
   public ThreadPoolTaskExecutor barmanThreadPool(@Value("${barman.count}") int barmanThreadCount) {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(barmanThreadCount);
      executor.setMaxPoolSize(barmanThreadCount);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("barman-");
      executor.initialize();
      executor.setWaitForTasksToCompleteOnShutdown(true);
      return executor;
   }

}

@Slf4j
@RequiredArgsConstructor
@RestController
class Drinker {
   private final Barman barman;

   private final ThreadPoolTaskExecutor barmanThreadPool;
   // TODO [1] inject and use a ThreadPoolTaskExecutor.submit
   // TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
   // TODO [3] Enable messaging...


   @GetMapping("ping")
   public String ping() throws ExecutionException, InterruptedException {
      return "I'm alive";
   }

   @GetMapping
   public CompletableFuture<DillyDilly> getDrinks() throws ExecutionException, InterruptedException {
      log.info("Enter the bar: " + barman.getClass());

      CompletableFuture<Beer> futureBeer = barman.getOneBeer()
          .thenApply(this::addIce);

      CompletableFuture<Vodka> futureVodka = barman.getOneVodka();

      CompletableFuture<DillyDilly> futureDilly =
          futureBeer.thenCombineAsync(futureVodka, DillyDilly::new, barmanThreadPool);

      log.info("Http handler finished");
     return futureDilly;
   }

   private Beer addIce(Beer beer) {
      log.info("adding ice");
      return beer;
   }
}

@Data
@Slf4j
class DillyDilly {
   private final Beer beer;
   private final Vodka vodka;

   DillyDilly(Beer beer, Vodka vodka) {
      log.info("Mixing");
      ThreadUtils.sleep(1000);
      this.beer = beer;
      this.vodka = vodka;
   }


   @Override
   public String toString() {
      return "DillyDilly{" +
             "beer=" + beer +
             ", vodka=" + vodka +
             '}';
   }
}


// BAD NEWS: the http


@Slf4j
@Service
class Barman {
   @Async("barmanThreadPool")
   public CompletableFuture<Beer> getOneBeer() {
      log.debug("Pouring Beer...");
      ThreadUtils.sleep(1000); /// externa sytems (API, device, something remote that takes time to reply).
      return completedFuture(new Beer());
   }

   @Async("barmanThreadPool")
   public CompletableFuture<Vodka> getOneVodka() {
      log.debug("Pouring Vodka...");
      ThreadUtils.sleep(2000);
      return completedFuture(new Vodka());
   }
}

@Data
class Beer {
   private final String type = "BLOND";
}

@Data
class Vodka {
   private final String type = "ABSLUT";
}
