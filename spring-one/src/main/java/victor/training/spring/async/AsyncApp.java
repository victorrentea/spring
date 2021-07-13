package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.ThreadUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
   public static void main(String[] args) {
      SpringApplication.run(AsyncApp.class, args).close(); // Note: .close added to stop executors after CLRunner finishes
   }

   @Bean
   public ThreadPoolTaskExecutor executor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(1);
      executor.setMaxPoolSize(1);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("bar-");
      executor.initialize();
      executor.setWaitForTasksToCompleteOnShutdown(true);
      return executor;
   }

}

@Slf4j
@Component
class Drinker implements CommandLineRunner {
   @Autowired
   private Barman barman;

   // TODO [1] inject and use a ThreadPoolTaskExecutor.submit
   // TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
   // TODO [3] Messaging...
   public void run(String... args) throws Exception {
      log.debug("Submitting my order");
		// cat timp astept la aceasta linie
      CompletableFuture<Beer> futureBeer = barman.getOneBeer(); // 0
      // nu te-ai blocat pana aici
      CompletableFuture<Vodka> futureVodka = barman.getOneVodka(); // 0

		Beer beer = futureBeer.get(); // 1 sec
		Vodka vodka = futureVodka.get(); // ~0 sec pt ca vodka SE VA FI TURNAT in paralel cu berea de la linia precedenta

		log.debug("Got my order! Thank you lad! " + Arrays.asList(beer, vodka));
   }
}

@Slf4j
@Service
class Barman {
   @Async
   public CompletableFuture<Beer> getOneBeer() {
      log.debug("Pouring Beer...");
      ThreadUtils.sleep(1000); // REST call / SOAL call
      return completedFuture(new Beer());
   }

   @Async
   public CompletableFuture<Vodka> getOneVodka() {
      log.debug("Pouring Vodka...");
      ThreadUtils.sleep(1000); // LONG DB QUERY
      return CompletableFuture.completedFuture(new Vodka());
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
