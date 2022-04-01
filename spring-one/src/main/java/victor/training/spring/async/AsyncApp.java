package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;
import victor.training.spring.web.SecurityConfig;
import victor.training.spring.web.controller.util.TestDBConnection;

import java.util.concurrent.CompletableFuture;

@EnableAsync
@SpringBootApplication
@Import(SecurityConfig.class)
public class AsyncApp {
	public static void main(String[] args) {

		new SpringApplicationBuilder(AsyncApp.class)
			.listeners(new TestDBConnection())
			.profiles("spa") // re-enables WEB nature (disabled in application.properties for the other apps not to start :8080)
			.run(args);
	}

	@Bean
	public ThreadPoolTaskExecutor vodkaPool(@Value("${vodka.thread.count}") int threadSize) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(threadSize);
		executor.setMaxPoolSize(threadSize);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("vodka-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}
	@Bean
	public ThreadPoolTaskExecutor beerPool(@Value("${beer.thread.count}") int threadSize) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(threadSize);
		executor.setMaxPoolSize(threadSize);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("beer-");
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
	@Autowired
	private ThreadPoolTaskExecutor vodkaPool;
	@Autowired
	private ThreadPoolTaskExecutor beerPool;

	@GetMapping("drink")
	public CompletableFuture<UBoat> drink() {
		log.debug("Submitting my order");

//		Mono.fromCallable(() -> barman.getOneBeer())
//			.subscribeOn(boundedElastic())

		// a safer, happir Mono
//		parallelStream()

		CompletableFuture<Beer> futureBeer = CompletableFuture.supplyAsync(() -> barman.getOneBeer(), beerPool); // in webflux is an antipattern
		CompletableFuture<Vodka> futureVodka = CompletableFuture.supplyAsync(() -> barman.getOneVodka(), vodkaPool);
//		Mono.zip
		CompletableFuture<UBoat> futureUBoat = futureBeer.thenCombine(futureVodka, (b, v) -> new UBoat(b, v));

		log.debug("Got my order! Thank you lad! " );
		return futureUBoat;
	}
}

class UBoat {
	private final Beer beer;
	private final Vodka vodka;
	UBoat(Beer beer, Vodka vodka) {
		this.beer = beer;
		this.vodka = vodka;
	}

	public Vodka getVodka() {
		return vodka;
	}

	public Beer getBeer() {
		return beer;
	}
}

@Slf4j
@Service
class Barman {
	public Beer getOneBeer() {
		 log.debug("Pouring Beer (REST call TAKES TIME)...");
		 // ~WebClient for non-webflux
//		AsyncRestTemplate rest = new AsyncRestTemplate();
//		CompletableFuture<ResponseEntity<Object>> completable = rest.exchange().completable();
		ThreadUtils.sleep(1000);
		 return new Beer();
	 }
	
	 public Vodka getOneVodka() {
		 log.debug("Pouring Vodka (SOAP call, 'FAT SQL' DB) ...");
		 ThreadUtils.sleep(1000);
		 return new Vodka();
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
