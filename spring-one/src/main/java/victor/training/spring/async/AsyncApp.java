package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

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
//	@ConfigurationProperties
	public ThreadPoolTaskExecutor executor(@Value("${barman.thread.count}")
															 int barmanThreadCount) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(barmanThreadCount);
		executor.setMaxPoolSize(barmanThreadCount);
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

//	private static final ExecutorService pool = Executors.newFixedThreadPool(2);
@Autowired
private ThreadPoolTaskExecutor pool;

	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	// TODO [3] Messaging...
	@GetMapping("drink")
	public List<Object> drink() throws Exception {
		log.debug("Submitting my order");

		Future<Beer> futureBeer = pool.submit(() -> barman.getOneBeer());
		Future<Vodka> futureVodka = pool.submit(() -> barman.getOneVodka());

		Beer beer = futureBeer.get(); // how much time is the HTTP thread blocked here ? 1s
		Vodka vodka = futureVodka.get(); // how much time is the HTTP thread blocked here ? 0s

		log.debug("Got my order! Thank you lad! " + Arrays.asList(beer, vodka));
		return List.of(beer,vodka);
	}
}

@Slf4j
@Service
class Barman {
	public Beer getOneBeer() {
		 log.debug("Pouring Beer...");
		 ThreadUtils.sleep(1000); // REST CALL
		 return new Beer();
	 }
	
	 public Vodka getOneVodka() {
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(1000); // FAT PIG SQL
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
