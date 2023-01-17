package victor.training.spring.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

	@Bean
	public ThreadPoolTaskExecutor executor(
			@Value("${barman.thread.pool.size}") int threadPoolSize) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(threadPoolSize);
		executor.setMaxPoolSize(threadPoolSize);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("bar-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}
}



