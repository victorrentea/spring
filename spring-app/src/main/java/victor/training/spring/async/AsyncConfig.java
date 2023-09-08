package victor.training.spring.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {


	@Bean
	public ThreadPoolTaskExecutor executor(@Value("${bar.pool.size}") int poolSize) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(poolSize); // minimum size
		executor.setMaxPoolSize(poolSize); // max how much it can extend under pressure
		executor.setQueueCapacity(500); // how many tasks can be waiting at some point
		executor.setThreadNamePrefix("bar-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}
}



