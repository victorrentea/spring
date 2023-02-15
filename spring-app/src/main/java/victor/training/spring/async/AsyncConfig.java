package victor.training.spring.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {
	@Bean
	@ConfigurationProperties(prefix = "bar.pool")
	public ThreadPoolTaskExecutor barPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(poolSize);
//		executor.setMaxPoolSize(poolSize);
		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("bar-"); // LOVE in log!
		executor.setTaskDecorator(new TaskDecorator() {
			@Override
			public Runnable decorate(Runnable runnable) {
				// here I am in the SUMBITTER THREAD (tocmcat's) original
				String metadata = ThreadLocalStuff.THREAD_LOCAL_DATA.get();
				return () -> {
					ThreadLocalStuff.THREAD_LOCAL_DATA.set(metadata);
					// here I am in the WORKER THREAD
					try {
						runnable.run();
					} finally {
						ThreadLocalStuff.THREAD_LOCAL_DATA.remove();
					}
				};
			}
		});

		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}
}



