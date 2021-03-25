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
import java.util.concurrent.ExecutionException;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
	public static void main(String[] args) {
		new SpringApplicationBuilder(AsyncApp.class)
			.profiles("spa") // re-enables WEB nature (disabled in application.properties for the other apps)
			.run(args);
	}

	@Bean
	public ThreadPoolTaskExecutor beerPool(@Value("${beer.count:1}")int barmanCount) {
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
	public ThreadPoolTaskExecutor vodkaPool(@Value("${vodka.count:4}")int barmanCount) {
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
class ProDrinker {
	@Autowired
	private Barman barman;
//	@Autowired
//	ThreadPoolTaskExecutor pool;

//		ExecutorService pool = Executors.newFixedThreadPool(40);
	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean

	// TODO [3] Messaging...

	@GetMapping
	public CompletableFuture<DillyDilly> drink() throws ExecutionException, InterruptedException {

		log.debug("Submitting my order");


		CompletableFuture<Beer> futureBeer = barman.getOneBeer();
		CompletableFuture<Vodka> futureVodka = barman.getOneVodka();

		log.debug("The waiter left with my order....");


		CompletableFuture<DillyDilly> futureDilly = futureBeer.thenCombine(futureVodka, (b, v) -> new DillyDilly(b, v));
		log.debug("EXITING HTTP THREAD");
		return futureDilly;
	}
}

class DillyDilly {
	private final Beer beer;
	private final Vodka vodka;

	DillyDilly(Beer beer, Vodka vodka) {
		this.beer = beer;
		this.vodka = vodka;
	}

	public Vodka getVodka() {
		return vodka;
	}

	public Beer getBeer() {
		return beer;
	}

	@Override
	public String toString() {
		return "DillyDilly{" +
				 "beer=" + beer +
				 ", vodka=" + vodka +
				 '}';
	}
}

@Slf4j
@Service
class Barman {

	@Async("beerPool")
	public CompletableFuture<Beer> getOneBeer() {
		 log.debug("Pouring Beer...");
		 ThreadUtils.sleep(1000); // DB mongo
		 return CompletableFuture.completedFuture(new Beer());
	 }

	 @Async("vodkaPool")
	 public CompletableFuture<Vodka> getOneVodka() {
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(1000); // REST API
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
