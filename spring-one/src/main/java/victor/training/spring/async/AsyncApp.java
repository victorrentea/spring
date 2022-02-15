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
			.profiles("spa")
			.run(args);
	}

//	@Value("${barman.thread.count}")
//	private int barmanThreadCount;

	@Bean
	public ThreadPoolTaskExecutor beerPool(@Value("${beer.thread.count}")
															 int barmanThreadCount) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(barmanThreadCount);
		executor.setMaxPoolSize(barmanThreadCount);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("beer-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}
	@Bean
	public ThreadPoolTaskExecutor vodkaPool(@Value("${vodka.thread.count}")
															 int barmanThreadCount) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(barmanThreadCount);
		executor.setMaxPoolSize(barmanThreadCount);
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

//	private static final ExecutorService pool = Executors.newFixedThreadPool(2);
	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	// TODO [3] Messaging...
	@GetMapping("drink")
	public CompletableFuture<DillyDilly> drink() throws Exception {
		log.debug("Submitting my order");

		CompletableFuture<Beer> futureBeer = barman.getOneBeer(); // nothing happens YET
		CompletableFuture<Vodka> futureVodka = barman.getOneVodka(); // = promise

		// IT IS ILLEGAL TO DO GET on a completableFuture
//		Beer beer = futureBeer.get(); // how much time is the HTTP thread blocked here ? 1s
//		Vodka vodka = futureVodka.get(); // how much time is the HTTP thread blocked here ? 0s

		CompletableFuture<DillyDilly> futureDilly = futureBeer.thenCombineAsync(futureVodka,
			(beer, vodka) -> new DillyDilly(beer, vodka));
		// Mono.zip

		log.debug("Got my order! ");
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
	@Async("beerPool") // means Proxy
	public CompletableFuture<Beer> getOneBeer() { // 2 BEERS MAX IN PARALLEL
		 log.debug("Pouring Beer...");
		 ThreadUtils.sleep(1000); // REST CALL
		 return CompletableFuture.completedFuture(new Beer());
	 }
	@Async("vodkaPool")
	 public CompletableFuture<Vodka> getOneVodka() { //8 SHOTS IN PARALLEL
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(1000); // FAT PIG SQL
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
