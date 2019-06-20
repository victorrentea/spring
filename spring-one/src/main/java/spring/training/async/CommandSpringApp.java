package spring.training.async;

import java.util.Arrays;
import java.util.concurrent.*;

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

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@EnableAsync
@SpringBootApplication
public class CommandSpringApp {
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(CommandSpringApp.class, args).close(); // Note: .close to stop executors after CLRunner finishes


	}

	@Bean
	public ThreadPoolTaskExecutor executor(@Value("${barman.thread.count:2}") int threadCount) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(threadCount);
		executor.setMaxPoolSize(threadCount);
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
	public void run(String... args) throws Exception {
		log.debug("Submitting my order catre un barman: " + barman.getClass());
		CompletableFuture<Ale> futureAle = barman.getOneAle();
		CompletableFuture<Whiskey> futureWhiskey = barman.getOneWhiskey();
		log.debug("A plecat fata cu comanda");

//		Ale ale = futureAle.get();
//		Whiskey whiskey = futureWhiskey.get();
//
//
//		log.debug("Got my order! Thank you lad! :  " + Arrays.asList(ale, whiskey));

		CompletableFuture.allOf(futureAle, futureWhiskey)
				.thenAcceptAsync(v -> {
					log.debug("Trimit acu");
					Future<Void> futureVoid = barman.injura_l("$%^!%$#!^@!%^#");// ii dau un SMS * anonim

					log.debug("Mi-a venit comanda. acum astept palma");
					try {
						futureVoid.get();
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				});


		log.debug("Aaahh! Plec acasa!");

		Thread.sleep(3001);
	}
}

@Slf4j
@Service
class Barman {
	@Async
	public CompletableFuture<Ale> getOneAle() {
		 log.debug("Pouring Ale...");
		 ThreadUtils.sleep(1000);
		 return CompletableFuture.completedFuture(new Ale());
	 }

	@Async
	 public CompletableFuture<Whiskey> getOneWhiskey() {
		 log.debug("Pouring Whiskey...");
		 ThreadUtils.sleep(1000);
		 return CompletableFuture.completedFuture(new Whiskey());
	 }

	 @Async
	public Future<Void> injura_l(String s) {
		throw new IllegalArgumentException("Te omor!!");
	}
}

@Data
class Ale {
}

@Data
class Whiskey {
}
