package victor.training.spring.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.ThreadUtils;

import java.util.Arrays;
import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;

@EnableAsync
@SpringBootApplication
public class AsyncApp {


	public static void main(String[] args) {
		SpringApplication.run(AsyncApp.class, args).close(); // Note: .close added to stop executors after CLRunner finishes
	}

	@Bean
	public ThreadPoolTaskExecutor executor(@Value("${barman.thread.count}") int barmanThreadCount) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(barmanThreadCount);
		executor.setMaxPoolSize(barmanThreadCount);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("bar-");
		executor.initialize();
		executor.setTaskDecorator(new TaskDecorator() {
			@Override
			public Runnable decorate(Runnable taskulSubmis) {
				long t0 = currentTimeMillis(); // ruleaza atunci cand se face submit()
				// TODO iei de pe threadLocal sau @Scope("thread")   ca rulezi in threadul care face submit()
				return () -> {
					long t1 = currentTimeMillis(); // am inceput sa rulez
					// TODO repui pe ThreadLocal sau @Scope thread in threadul worker
					long waitingTimeInQueue = t1 - t0;
					taskulSubmis.run();
					long t2 = currentTimeMillis();
					long runTime=t2-t1;
				};
			}
		});
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

}

@Slf4j
@Component
class Drinker implements CommandLineRunner {
	@Autowired
	private Barman barman;

	@Autowired
	ThreadPoolTaskExecutor executor;

	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	// TODO [3] Messaging...
//	@PostConstruct
//	@EventListener(ApplicationContextInitializedEvent.class)
//	public void method() {
	public void run(String... args) throws Exception {
		log.debug("Submitting my order");

		Future<Beer> futureBeer = executor.submit(() -> barman.getOneBeer());
		Future<Vodka> futureVodka = executor.submit(() -> barman.getOneVodka());

		log.debug("AICI in paralel se pornesc 2 executii pe threaduri separate");

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
		 ThreadUtils.sleep(1000);
		 return new Beer();
	 }
	
	 public Vodka getOneVodka() {
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(1000);
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
