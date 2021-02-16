package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    public CompletableFuture<DillyDilly> beau() throws ExecutionException, InterruptedException {
      log.debug("Submitting my order to "  +barman.getClass());

      CompletableFuture<Beer> futureBeer = barman.getOneBeer();
      CompletableFuture<Vodka> futureVodka = barman.getOneVodka();

      log.debug("A plecat fata cu comanda");
      // 2 blocarea threadului MEU pana cand ala e gata

//      Beer beer = futureBeer.get(); // HTTP thread sta blocat 1 sec
//      Vodka vodka = futureVodka.get(); // aici nu sta deloc

      CompletableFuture<DillyDilly> futureDilly = futureBeer
          .thenCombineAsync(futureVodka, (b, v) -> new DillyDilly(b, v));


      barman.injura("&$%@!$@%$!^*@&%*!");

      log.debug("Ies din http handler is ma bag la loc in patuc.");
      return futureDilly;
   }


//   public void vreauOBere() throws ExecutionException, InterruptedException {
//      Beer beer = barman.getOneBeer().get();
//   }
}

// use-case pt @Async;
// 1) rularea unor apeluri blocante pe pooluri dedicate ca sa pot sa fac throttling: cate th paralele suporta ala
// 2) Sa lansez in paralel 2 taskuri scumpe ca sa castig timp
// 3) (fitza) sa deblochezi threadul de http -------> Reactive Programming

// 4) Fire and forget:


@Slf4j
@Service
class Barman {

   @Async("beerPool")
   public CompletableFuture<Beer> getOneBeer() {
      if (true) {
         throw new IllegalArgumentException("NU mai e bere!");
      }
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

   @Async
   public void injura(String uratura) {
      log.debug("Citesc uratura: {} ", uratura);
      ThreadUtils.sleep(1000); // inchipuie: batch, job de parsare, zip, ceva greu minute multe
      if (StringUtils.isNotBlank(uratura)) {
         throw new IllegalArgumentException("Iti fac buzunar!");
      }
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
