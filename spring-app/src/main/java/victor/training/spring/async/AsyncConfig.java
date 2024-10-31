package victor.training.spring.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClient;

@EnableAsync
@Configuration
public class AsyncConfig {
	;
	@Bean
	@ConfigurationProperties(prefix="pool.bar")
	public ThreadPoolTaskExecutor poolBar(
			TaskDecorator taskDecorator/*,
			@Value("${pool.bar.size}") int poolBarSize*/) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
/*		executor.setCorePoolSize(poolBarSize);
		executor.setMaxPoolSize(poolBarSize);*/
//		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("pool-bar-");
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



