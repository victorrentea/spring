package victor.training.spring.async;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
		executor.setTaskDecorator(new CopyMDCToWorker());

		Gauge.builder( "poolbar.pool.size", executor::getPoolSize).register(Metrics.globalRegistry);
		Gauge.builder( "poolbar.queue.size", executor::getQueueSize).register(Metrics.globalRegistry);
		return executor;
	}

}




