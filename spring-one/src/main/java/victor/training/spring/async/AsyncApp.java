package victor.training.spring.async;

import com.sun.tracing.dtrace.FunctionAttributes;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.ThreadUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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

	public void uploadFile(HttpServletRequest request) throws IOException {
		File tempFile = Files.createTempFile("data", ".dat").toFile();
		try (OutputStream tempOutputStream = new FileOutputStream(tempFile)) {
			IOUtils.copy(request.getInputStream(), tempOutputStream);
		}
//		if (tempFile.length() > 10_000_000) {
//			throw
//		}
		// procesezi ACUM sau chemand o @Async
//		altaClasa.processFile(tempFile);
	}

	@Async // ! din alta clasa
	public void processFile(File file) {

	}



	private final Barman barman;

	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	// TODO [3] Enable messaging...
	public void run(String... args) throws Exception {
		Thread.sleep(3000);
		log.debug("Submitting my order to " + barman.getClass());

		CompletableFuture<Beer> futureBeer = barman.getOneBeer();
		CompletableFuture<Vodka> futureVodka = barman.getOneVodka();

		CompletableFuture<DillyDilly> futureDilly = futureBeer
			.thenCombine(futureVodka, (b, v) -> new DillyDilly(b, v));

		futureDilly.thenAccept(dilly -> log.debug("Got my order! Thank you lad! " + dilly));

		// barman.getOneBeer().get(); pt throttling de threaduri cand chemi un sistem extern

		log.debug("Main-ul pleaca acasa");
	}
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
