package victor.training.spring.async;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	public ThreadPoolTaskExecutor executor(@Value("${barman.thread.count}") int maxPoolSize) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(maxPoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("bar-");
		executor.initialize();
//		executor.setTaskDecorator();// geek stuff
//		executor.setThreadFactory(() -> new Thread().setUncaughtExceptionHandler());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

}

@Slf4j
@RequiredArgsConstructor
@Component
class ProDrinker implements CommandLineRunner {
	private final Barman barman;
	private final ThreadPoolTaskExecutor executor;


	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	// TODO [3] Messaging...
	public void run(String... args) throws Exception {
		log.debug("Submitting my order");

		Future<Beer> futureBeer = executor.submit(barman::getOneBeer);
		Future<Vodka> futureVodka = executor.submit(barman::getOneVodka);


		Beer beer = futureBeer.get();
		Vodka vodka = futureVodka.get();

		log.debug("Got my order! Thank you lad! " + Arrays.asList(beer, vodka));
	}
}

@Slf4j
@Service
class Barman {
	public Beer getOneBeer() {
		 log.debug("Pouring Beer...");
		 ThreadUtils.sleep(1000); // THE expensive get Policy call
		 return new Beer();
	 }
	
	 public Vodka getOneVodka() {
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(1000); // some other expensive "micro"service
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
