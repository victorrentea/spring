package victor.training.spring.async;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.completedFuture;

@EnableAsync
@EnableRetry
@SpringBootApplication
public class AsyncApp {
	public static void main(String[] args) {

		new SpringApplicationBuilder(AsyncApp.class)
			.profiles("spa") // re-enables WEB nature (disabled in application.properties for the other apps)
			.run(args);
	}


	@Bean
	public ThreadPoolTaskExecutor beerExecutor(@Value("${vodka.service.client.thread.count}") int maxPoolSize) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(maxPoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("b-");
		executor.initialize();
//		executor.setTaskDecorator();// geek stuff
//		executor.setThreadFactory(() -> new Thread().setUncaughtExceptionHandler());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}
	@Bean
	public ThreadPoolTaskExecutor vodkaExecutor(@Value("${vodka.service.client.thread.count}") int maxPoolSize) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(maxPoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("v-");
		executor.initialize();
//		executor.setTaskDecorator();// geek stuff
//		executor.setThreadFactory(() -> new Thread().setUncaughtExceptionHandler());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

}

@Slf4j
@RequiredArgsConstructor
@RestController
class ProDrinker  {
	private final Barman barman;


	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	// TODO [3] Messaging...
	@GetMapping("drinks")
	public CompletableFuture<DillyDilly> method() throws ExecutionException, InterruptedException {
		log.debug("Submitting my order");

		CompletableFuture<Beer> futureBeer = barman.getOneBeer(UUID.randomUUID().toString());
		CompletableFuture<Vodka> futureVodka = barman.getOneVodka();

		CompletableFuture<DillyDilly> futureDilly = futureBeer.thenCombine(futureVodka, (beer, vodka) -> new DillyDilly(beer, vodka));

		futureDilly.thenAccept(dilly -> log.info("Drinking " + dilly));
		futureDilly.thenRun(barman::callWife);

		log.info("main thread leaves the party");
		return futureDilly;

//		DillyDilly dillyDilly = futureDilly.get();
//		return dillyDilly;
//		Beer beer = futureBeer.get();
//		Vodka vodka = futureVodka.get();
//		log.debug("Got my order! Thank you lad! " + Arrays.asList(beer, vodka))
	}
}
@Slf4j
@Data
class DillyDilly {
	private final Beer beer;
	private final Vodka vodka;

	DillyDilly(Beer beer, Vodka vodka) {
		log.info("Mixking dilly");
		ThreadUtils.sleep(1000);
		this.beer = beer;
		this.vodka = vodka;
	}
}

// throttling

@Slf4j
@Service
class Barman {

	@Async
	public void callWife() { // fire and forget action
		log.info("call wife: I'm coming home");
	}

	@Async("beerExecutor")
	@Retryable // << very powerful
	public CompletableFuture<Beer> getOneBeer(String requestId) {  // === promises   q.all(q1,q2,q3).then(function() {})
		log.debug("Pouring Beer... PUT to bar.com/beer/" + requestId);
		if (Math.random() < .5) {
			throw new IllegalArgumentException();
		}
		 log.debug("POST Poured Beer... ");
		 ThreadUtils.sleep(1000); // THE expensive get Policy call
		 return completedFuture(new Beer());
	 }

	 @Async("vodkaExecutor")
	 public CompletableFuture<Vodka> getOneVodka() {
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(300); // some other expensive "micro"service
		 return completedFuture(new Vodka());
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
