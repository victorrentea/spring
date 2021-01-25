package victor.training.spring.async;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.concurrent.*;

import static java.util.concurrent.CompletableFuture.completedFuture;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
	public static void main(String[] args) {
		SpringApplication.run(AsyncApp.class, args).close(); // Note: .close added to stop executors after CLRunner finishes
	}


	@Bean // defined a spring bean named "executor" of type ThreadPoolTaskExceutor
	public ThreadPoolTaskExecutor executor(@Value("${barman.count}") int barmanThreadCount) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(barmanThreadCount);
		executor.setMaxPoolSize(barmanThreadCount);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("barman-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

}

@Slf4j
@Component
@RequiredArgsConstructor
class Drinker implements CommandLineRunner {
	private final Barman barman;
	private final ThreadPoolTaskExecutor pool;

	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	// TODO [3] Enable messaging...
	public void run(String... args) throws Exception {
		log.info("Enter the bar: " + barman.getClass());

//		ExecutorService pool = Executors.newFixedThreadPool(2);

		Future<Beer> futureBeer = barman.getOneBeer();
		Future<Vodka> futureVodka = barman.getOneVodka();

		Beer beer = futureBeer.get(); // how much time it take to complete ? - 1
		Vodka vodka = futureVodka.get();// how much time it take to complete ? - 0

		log.info("Drinking {} and {}", beer, vodka );
	}
}

@Slf4j
@Service
class Barman {
	@Async
	public CompletableFuture<Beer> getOneBeer() {
		 log.debug("Pouring Beer...");
		 ThreadUtils.sleep(1000); /// externa sytems (API, device, something remote that takes time to reply).
		 return completedFuture(new Beer());
	 }
	 @Async
	public CompletableFuture<Vodka> getOneVodka() {
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(1000);
		return completedFuture(new Vodka());
	 }
}

@Data
class Beer {
}

@Data
class Vodka {
}
