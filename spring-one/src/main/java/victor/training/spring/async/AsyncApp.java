package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
		ExecutorService pool = Executors.newFixedThreadPool(2);


		Future<Beer> futureBeer = pool.submit(() -> barman.getOneBeer());
		Future<Vodka> futureVodka = pool.submit(() -> barman.getOneVodka());

		log.debug("Aici a plecata fata cu comenzile mele");

		Beer beer = futureBeer.get(); // aici blochez threadul curent (main) - 1s
		Vodka vodka = futureVodka.get(); // aici nu mai ma blochez de loc, vodka a fost turnata in parallel

		log.debug("Got my order! Thank you lad! " + Arrays.asList(beer, vodka));
	}
}

@Slf4j
@Service
class Barman {
	public Beer getOneBeer() {
		 log.debug("Pouring Beer...");
		 ThreadUtils.sleep(1000); // RMI call
		 return new Beer();
	 }
	
	 public Vodka getOneVodka() {
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(1000); // SOAP call
		 return new Vodka();
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
