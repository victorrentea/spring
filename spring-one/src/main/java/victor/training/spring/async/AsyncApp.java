package victor.training.spring.async;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.concurrent.*;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
	public static void main(String[] args) {
		SpringApplication.run(AsyncApp.class, args).close(); // Note: .close added to stop executors after CLRunner finishes
	}

//	@Bean
//	public ThreadPoolTaskExecutor executor() {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(2);
//		executor.setMaxPoolSize(2);
//		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("bar-");
//		executor.initialize();
//		executor.setWaitForTasksToCompleteOnShutdown(true);
//		return executor;
//	}

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
		log.debug("Submitting my order to "  + barman.getClass());

		Future<Beer> futureBeer = barman.getOneBeer();
		Future<Vodka> futureVodka = barman.getOneVodka();
		log.debug("Fata mi-a luat comanda! si s-a dus cu ea...");
		Beer beer = futureBeer.get();
		Vodka vodka = futureVodka.get();
		log.debug("Got my order! Thank you lad! " + Arrays.asList(beer, vodka));
	}
}


//@Getter
//@Setter
//@Entity
//class User {
//	@Id
//		@GeneratedValue
//		@Setter(AccessLevel.NONE)
//	Long id;
//	String name;
//}

@Slf4j
@Service
class Barman {
	@Async/*("executor")*/
	public CompletableFuture<Beer> getOneBeer() {
		 log.debug("Pouring Beer...");
		 ThreadUtils.sleep(1000); // Apel de API extern
		 return CompletableFuture.completedFuture(new Beer());
	 }

	@Async
	 public CompletableFuture<Vodka> getOneVodka() {
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(1000); // DB SELECT masiv
		 return CompletableFuture.completedFuture(new Vodka());
	 }
}

@Data
class Beer {
	public static final String type = "blond";
}

@Data
class Vodka {
	public static final String type = "deadly";
}
