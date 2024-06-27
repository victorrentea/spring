package victor.training.spring.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@EnableScheduling
@Configuration
public class AsyncConfig {

	@Bean
	@ConfigurationProperties(prefix = "executor")
	public ThreadPoolTaskExecutor executor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(threadCount);
//		executor.setMaxPoolSize(threadCount);
//		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("bar-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}
//	@Bean // initial
//	public ThreadPoolTaskExecutor executor(
//			@Value("${executor.thread.count}")
//			int threadCount
//	) {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(threadCount);
//		executor.setMaxPoolSize(threadCount);
//		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("bar-");
//		executor.initialize();
//		executor.setWaitForTasksToCompleteOnShutdown(true);
//		return executor;
//	}
}



