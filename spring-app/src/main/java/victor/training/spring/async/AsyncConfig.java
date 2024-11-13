package victor.training.spring.async;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

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
		executor.setTaskDecorator(new TaskDecorator() {
			@Override
			public Runnable decorate(Runnable taskulDeRulat) {
				// sunt in threadul celui care cere Th "parinte"
				Map<String, String> parentMDC = MDC.getCopyOfContextMap();
				return ()-> { // thread hopping
					MDC.setContextMap(parentMDC);
					//sunt in worker threadul din pool Th "copil"
          taskulDeRulat.run();
        };
			}
		});
		return executor;
	}
}



