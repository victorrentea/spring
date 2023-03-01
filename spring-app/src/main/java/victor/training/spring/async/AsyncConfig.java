package victor.training.spring.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@EnableAsync
@Configuration
public class AsyncConfig {

	@Bean
	@ConfigurationProperties("bar-pool")
	public ThreadPoolTaskExecutor barPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}



//	@Bean
//	public ThreadPoolTaskExecutor barPool(
//					@Value("${bar.pool.size}") int barPoolSize,
//					Xy xy) {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(barPoolSize);
//		executor.setMaxPoolSize(barPoolSize);
//		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("bar❤️-");
//		executor.initialize();
//		executor.setWaitForTasksToCompleteOnShutdown(true);
//		return executor;
//	}
}

@Component
class Xy {

}

