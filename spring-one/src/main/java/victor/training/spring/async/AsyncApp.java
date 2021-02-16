package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;
import victor.training.spring.web.SpaApplication;

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
    public String beau() throws ExecutionException, InterruptedException {
      log.debug("Submitting my order to "  +barman.getClass());

      Future<Beer> futureBeer = barman.getOneBeer();
      Future<Vodka> futureVodka = barman.getOneVodka();

      log.debug("A plecat fata cu comanda");
      // 2 blocarea threadului MEU pana cand ala e gata
      Beer beer = futureBeer.get();
      Vodka vodka = futureVodka.get();

      List<Object> bauturi = Arrays.asList(beer, vodka);
      log.debug("Got my order! Thank you lad! " + bauturi);
      // 3 callbacks ---> Iad
      return bauturi.toString();
   }
//   public void vreauOBere() throws ExecutionException, InterruptedException {
//      Beer beer = barman.getOneBeer().get();
//   }
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
