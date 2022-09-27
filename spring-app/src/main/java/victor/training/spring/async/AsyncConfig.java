package victor.training.spring.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {
	@Bean
	public ThreadPoolTaskExecutor beerPool(@Value("${beer.pool.size}") int barPoolSize) {
//		Schedulers.newBoundedElastic
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(barPoolSize);
		executor.setMaxPoolSize(barPoolSize);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("beer-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}
	@Bean
	public ThreadPoolTaskExecutor vodkaPool(@Value("${vodka.pool.size}") int vodkaPoolSize) {
//		Schedulers.newBoundedElastic
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(vodkaPoolSize);
		executor.setMaxPoolSize(vodkaPoolSize);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("vodka-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

//	@Autowired
//	ThreadPoolTaskExecutor executor;
}



