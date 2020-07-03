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

import java.util.Arrays;
import java.util.concurrent.*;

import static java.util.concurrent.CompletableFuture.completedFuture;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
	public static void main(String[] args) {
		SpringApplication.run(AsyncApp.class, args).close(); // Note: .close added to stop executors after CLRunner finishes
	}

	@Bean
	public ThreadPoolTaskExecutor barmanExecutor(@Value("${barman.count}") int barmanCount) {
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
@RequiredArgsConstructor
class Drinker implements CommandLineRunner {
	private final Barman barman;

	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	// TODO [3] Enable messaging...
	public void run(String... args) throws Exception {
		Thread.sleep(3000);
		log.debug("Submitting my order to " + barman.getClass());

		Future<Beer> futureBeer = barman.getOneBeer();
		Future<Vodka> futureVodka = barman.getOneVodka();

		Beer beer = futureBeer.get();
		Vodka vodka = futureVodka.get();

		// barman.getOneBeer().get(); pt throttling de threaduri cand chemi un sistem extern

		log.debug("Got my order! Thank you lad! " + Arrays.asList(beer, vodka));
	}
}

@Slf4j
@Service
class Barman {
	 // ganditi-va la un apel de REST catre un serviciu extern
	@Async("barmanExecutor")
	public CompletableFuture<Beer> getOneBeer() {
		 log.debug("Pouring Beer...");
		 ThreadUtils.sleep(1000);
		 return completedFuture(new Beer());
	 }
	 @Async("barmanExecutor")
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


//@Service
//class OrderService {
//	private final OrderRepo orderRepo;
//	private final CustomerRepo customerRepo;
//
//	public void n() {
//
//	}
//}

//@Service
//class Class2Service {
//	private final CustomerRepo customerRepo;
//	private final AltService altService;
//	public void m() {
//
//	}
//}
