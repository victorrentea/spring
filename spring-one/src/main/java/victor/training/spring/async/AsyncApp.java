package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;
import victor.training.spring.web.SpaApplication;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@EnableAsync
@SpringBootApplication
public class AsyncApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AsyncApp.class)
			.profiles("spa")
			.run(args);
	}

	@Bean
	public ThreadPoolTaskExecutor executor(@Value("${barman.count}") int barmanCount) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(barmanCount);
		executor.setMaxPoolSize(barmanCount);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("bar-");
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
	ThreadPoolTaskExecutor pool;

	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	// TODO [3] Messaging...
	@GetMapping
	public DillyDilly getDrinks() throws ExecutionException, InterruptedException {
		log.debug("Submitting my order");
//		ExecutorService pool = Executors.newFixedThreadPool(2);

		// 1 mangeuit pool cu spring DON
		// TODO 2 @Async
		// TODO 3 Sa nu mai blochez de loc main()
		// TODO 4 Endpoint HTTP asincron --- Reactive Programming: non-blocking request handling

		Future<Beer> futureBeer = pool.submit(() -> barman.getOneBeer());
		Future<Vodka> futureVodka = pool.submit(() -> barman.getOneVodka());

		log.debug("Aici a plecata fata cu comenzile mele");

		Beer beer = futureBeer.get(); // aici blochez threadul curent (main) - 1s
		Vodka vodka = futureVodka.get(); // aici nu mai ma blochez de loc, vodka a fost turnata in parallel

		DillyDilly dilly = new DillyDilly(beer, vodka);
		log.debug("Got my order! Thank you lad! " + dilly);
		return dilly;
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
	public String type = "blond";
}

@Data
class Vodka {
	public String type = "deadly";
}
