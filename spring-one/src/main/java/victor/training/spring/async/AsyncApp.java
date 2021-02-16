package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@EnableAsync
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class})
public class AsyncApp {
   public static void main(String[] args) {

      new SpringApplicationBuilder(AsyncApp.class)
          .profiles("spa")
          .run(args);

//      SpringApplication.run(AsyncApp.class, args); // Note: .close added to stop executors after CLRunner finishes
   }

   @Bean
   public ThreadPoolTaskExecutor beerPool(@Value("${barman.count}") int poolSize) {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(poolSize);
      executor.setMaxPoolSize(poolSize);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("beer-");
      executor.initialize();
      executor.setWaitForTasksToCompleteOnShutdown(true);
      return executor;
   }

   @Bean
   public ThreadPoolTaskExecutor vodkaPool(@Value("${barman.count}") int poolSize) {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(poolSize);
      executor.setMaxPoolSize(poolSize);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("vodka-");
      executor.initialize();
      executor.setWaitForTasksToCompleteOnShutdown(true);
      return executor;
   }

}

@Slf4j
@RestController
class Drinker  {
   @Autowired
   private Barman barman;

//   @Autowired
//   private ThreadPoolTaskExecutor barmanPool;

   // TODO [1] inject and use a ThreadPoolTaskExecutor.submit
   // TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
   // TODO [3] Enable messaging...


   @GetMapping
    public DillyDilly beau() throws ExecutionException, InterruptedException {
      log.debug("Submitting my order to "  +barman.getClass());

      Future<Beer> futureBeer = barman.getOneBeer();
      Future<Vodka> futureVodka = barman.getOneVodka();

      log.debug("A plecat fata cu comanda");
      // 2 blocarea threadului MEU pana cand ala e gata

      Beer beer = futureBeer.get(); // HTTP thread sta blocat 1 sec
      Vodka vodka = futureVodka.get(); // aici nu sta deloc

      List<Object> bauturi = Arrays.asList(beer, vodka);
      log.debug("Got my order! Thank you lad! " + bauturi);
      // 3 callbacks ---> Iad
      return new DillyDilly(beer, vodka);
   }
//   public void vreauOBere() throws ExecutionException, InterruptedException {
//      Beer beer = barman.getOneBeer().get();
//   }
}


class DillyDilly {
   private static final Logger log = LoggerFactory.getLogger(DillyDilly.class);
   private final Beer beer;
   private final Vodka vodka;

   DillyDilly(Beer beer, Vodka vodka) {
      this.beer = beer;
      this.vodka = vodka;
      log.debug("Mixing Dilly");
      ThreadUtils.sleep(1000);
   }

   public Beer getBeer() {
      return beer;
   }

   public Vodka getVodka() {
      return vodka;
   }
}

@Slf4j
@Service
class Barman {
   @Async("beerPool")
   public CompletableFuture<Beer> getOneBeer() {
      log.debug("Pouring Beer...");
      ThreadUtils.sleep(1000); // inchipuiti aici orice NETWORK CALL: db, select in mongo, Files pe disk, send kafka
      return CompletableFuture.completedFuture(new Beer());
   }

   @Async("vodkaPool")
   public CompletableFuture<Vodka> getOneVodka() {
      log.debug("Pouring Vodka...");
      ThreadUtils.sleep(1000);
      return CompletableFuture.completedFuture(new Vodka());
   }
}

@Data
class Beer {
   private final String type = "Blond";
}

@Data
class Vodka {
   private final String type = "tare";
}
