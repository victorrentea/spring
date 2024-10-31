package victor.training.spring.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClient;

@EnableAsync
@Configuration
public class AsyncConfig {
	@Value("${pool.bar.size}")
	private int poolBarSize;
	@Bean
	public ThreadPoolTaskExecutor poolBar(
			TaskDecorator taskDecorator) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(poolBarSize);
		executor.setMaxPoolSize(poolBarSize);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("pool-bar-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setTaskDecorator(taskDecorator);
		return executor;
	}


	@Bean
	public RestClient restClientPtBauturi() {
//		return RestClient.create();
		return RestClient.builder()
				.defaultHeader("Authorization", "Basic blabla")
				.build();
	}
	@Bean
	public RestClient restClientPtRomtelecom() {
//		return RestClient.create();
		return RestClient.builder()
				.defaultHeader("Authorization", "Basic romtelecom")
				.build();
	}
}



