package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.ThreadUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
	public static void main(String[] args) {
		SpringApplication.run(AsyncApp.class, args)/*.close()*/; // Note: .close added to stop executors after CLRunner finishes
	}

	@Bean(initMethod = "initialize")
	@ConfigurationProperties(prefix="myexecutor")
	public ThreadPoolTaskExecutor executor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(1);
//		executor.setMaxPoolSize(1);
//		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("bar-");
//		executor.setWaitForTasksToCompleteOnShutdown(true);
//		executor.initialize();
		return executor;
	}
//	@Bean(initMethod = "initialize")
//	@ConfigurationProperties(prefix="myexecutor")
//	public ThreadPoolTaskExecutor beerExecutor() {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
////		executor.setCorePoolSize(1);
////		executor.setMaxPoolSize(1);
////		executor.setQueueCapacity(500);
////		executor.setThreadNamePrefix("bar-");
////		executor.setWaitForTasksToCompleteOnShutdown(true);
////		executor.initialize();
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
		log.debug("Submitting my order");
		CompletableFuture<Beer> beerFuture = barman.getOneBeer();
		CompletableFuture<Vodka> vodkaFuture = barman.getOneVodka();

//		Beer beer = beerFuture.get();
//		Vodka vodka = vodkaFuture.get();
		beerFuture.thenAcceptBothAsync(vodkaFuture, (beer,vodka) ->
			log.debug("Got my order! Thank you lad! " + Arrays.asList(beer, vodka))
		);
	}
}

@Slf4j
@Service
class Barman {
	@Async//("beerExecutor")
	public CompletableFuture<Beer> getOneBeer() {
		 log.debug("Pouring Beer...");
		 ThreadUtils.sleep(1000);
		 return CompletableFuture.completedFuture(new Beer());
	 }

	 @Async
	 public CompletableFuture<Vodka> getOneVodka() {
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(1000);
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
