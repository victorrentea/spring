package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.ThreadUtils;

import java.util.Arrays;
import java.util.concurrent.Future;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
	public static void main(String[] args) {
		SpringApplication.run(AsyncApp.class, args).close(); // Note: .close added to stop executors after CLRunner finishes
	}

	@Bean
	public ThreadPoolTaskExecutor executor(@Value("${thread.count}") int threadSize) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(threadSize);
		executor.setMaxPoolSize(threadSize);
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
	@Autowired
	private ThreadPoolTaskExecutor executor;

	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	// TODO [3] Messaging...
	public void run(String... args) throws Exception {
		log.debug("Submitting my order");

//		Mono.fromCallable(() -> barman.getOneBeer())
//			.subscribeOn(boundedElastic())

		Future<Beer> futureBeer = executor.submit(() -> barman.getOneBeer()); // in webflux is an antipattern
		Future<Vodka> futureVodka = executor.submit(() -> barman.getOneVodka());

		Beer beer = futureBeer.get();
		Vodka vodka = futureVodka.get();

		log.debug("Got my order! Thank you lad! " + Arrays.asList(beer, vodka));
	}
}

@Slf4j
@Service
class Barman {
	public Beer getOneBeer() {
		 log.debug("Pouring Beer (REST call TAKES TIME)...");
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
