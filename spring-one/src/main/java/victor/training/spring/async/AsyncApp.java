package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@EnableAsync
@SpringBootApplication
public class AsyncApp {
	public static void main(String[] args) {
		SpringApplication.run(AsyncApp.class, args).close(); // Note: .close added to stop executors after CLRunner finishes
	}
	@Value("${barman.count}")
	private int barmanCount;

	@Bean
	public ThreadPoolTaskExecutor executor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(barmanCount);
		executor.setMaxPoolSize(barmanCount);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("barman-");
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
	// TODO [3] Enable messaging...
	public void run(String... args) throws Exception {
		Thread.sleep(3000);
		log.debug("Submitting my order " + barman.getClass());
		CompletableFuture<Beer> futureBeer = barman.getOneBeer();
		CompletableFuture<Vodka> futureVodka = barman.getOneVodka();

		log.debug("Astept sa vina beuturile. bat darabana");
		Beer beer = futureBeer.get(); // 1sec
		Vodka vodka = futureVodka.get(); // 1sec

		log.debug("Got my order! Thank you lad! " + Arrays.asList(beer, vodka));
		// ziua in care incepi sa scrii multithreading code este si ziua in care iti faci viata multithreading: pe un thread fixezi bugurile produse, pe altul: bestjobs, ejobs, monster, linkein
	}
}

@Slf4j
@Service
class Barman {
	@Async
	public CompletableFuture<Beer> getOneBeer() {
		 log.debug("Pouring Beer..."); // apel de REST externs
		 ThreadUtils.sleep(1000);
		 return CompletableFuture.completedFuture(new Beer());
	 }

	 @Async
	 public CompletableFuture<Vodka> getOneVodka() {
		 log.debug("Pouring Vodka..."); // apel de REST externs
		 ThreadUtils.sleep(1000);
		 return CompletableFuture.completedFuture(new Vodka());
	 }
}

@Data
class Beer {
}

@Data
class Vodka {
}
