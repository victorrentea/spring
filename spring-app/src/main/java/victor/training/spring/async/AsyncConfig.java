package victor.training.spring.async;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.concurrent.CompletableFuture;

@EnableAsync
@Configuration
public class AsyncConfig {
	@Bean
	public ThreadPoolTaskExecutor poolBar(TaskDecorator taskDecorator) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("pool-bar-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// copy MDC from parent thread to workerthread
		executor.setTaskDecorator(new TaskDecorator() {
			@Override
			public Runnable decorate(Runnable runnable) {
				var parent = MDC.getCopyOfContextMap();
				return () -> {
					if (parent != null) {
						MDC.setContextMap(parent); // on the child thread
					}
					try {
						runnable.run();
					}finally {
						MDC.clear();
					}
				};
			}
		});
		return executor;
	}

}




