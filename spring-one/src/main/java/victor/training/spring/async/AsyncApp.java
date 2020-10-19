package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import reactor.core.publisher.Mono;
import victor.training.spring.ThreadUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static reactor.core.publisher.Mono.fromFuture;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
	public static void main(String[] args) {
		SpringApplication.run(AsyncApp.class, args).close(); // Note: .close added to stop executors after CLRunner finishes
//		ThreadUtils.sleep(5000);
	}

	@Bean
	public ThreadPoolTaskExecutor barmanEx(@Value("${barman.count}")int barmanThreadCount) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(barmanThreadCount);
		executor.setMaxPoolSize(barmanThreadCount);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("barman-");
		executor.setDaemon(false);

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
		log.debug("Submitting my order to " + barman.getClass());

//		Mono.zip(fromFuture(barman.getOneBeer()), fromFuture(barman.getOneVodka()))
//			.map(tuple->new DillyDilly(tuple.getT1(), tuple.getT2()))
//			.subscribe(dilly->log.debug("Got my order! Thank you lad! " +dilly));


		barman.getOneBeer()
			.thenCombine(barman.getOneVodka(), DillyDilly::new)
			.thenAccept(dilly ->
				log.debug("Got my order! Thank you lad! " +dilly)
			);

		// Alergie la @Async:
//		Future<Beer> submit = barmanEx.submit(barman::getOneBeer);
//		Beer beer = futureBeer.get(); // asta ia 0.9999 sec
//		Vodka vodka = futureVodka.get(); // stam 0 sec,  ca vodka e deja turnata
	}

	@Autowired
	private ThreadPoolTaskExecutor barmanEx;
}


