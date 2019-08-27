package victor.training.spring.command;

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

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.completedFuture;

@EnableAsync
@SpringBootApplication
public class CommandSpringApp {
	public static void main(String[] args) {
		SpringApplication.run(CommandSpringApp.class, args).close(); // Note: .close to stop executors after CLRunner finishes
	}


	//--barman.thread.count=1
	//-Dbarman.thread.count=1
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
class Beutor implements CommandLineRunner {
	@Autowired
	private Barman barman;
	@Autowired
	ThreadPoolTaskExecutor pool;

	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
    // TODO [3] wanna try it out over JMS? try out ServiceActivatorPattern
	public void run(String... args) throws Exception {
		log.debug("Submitting my order to : " + barman.getClass());

		CompletableFuture<Ale> futureAle = barman.getOneAle();
		CompletableFuture<Whiskey> futureWhiskey = barman.getOneWhiskey();

		allOf(futureAle, futureWhiskey).thenRun(() -> {
			System.out.println("Gata");
		});

		log.debug("A plecat fata cu comanda");
		Ale ale = futureAle.get();
		Whiskey whiskey = futureWhiskey.get();

		log.debug("Got my order! Thank you lad! " + Arrays.asList(ale, whiskey));
	}
}

@Slf4j
@Service
class Barman {
	@Async
	public CompletableFuture<Ale> getOneAle() {
		 log.debug("Pouring Ale...");
		 ThreadUtils.sleep(1000);
		 return completedFuture(new Ale());
	 }

	@Async
	 public CompletableFuture<Whiskey> getOneWhiskey() {
		 log.debug("Pouring Whiskey...");
		 ThreadUtils.sleep(1000);
		 return completedFuture(new Whiskey());
	 }
}

@Data
class Ale {
}

@Data
class Whiskey {
}
