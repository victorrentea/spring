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
	public ThreadPoolTaskExecutor poolBar() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("pool-bar-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setTaskDecorator(new CopyMDCToWorker()); // copies TraceID

		Gauge.builder( "poolbar_pool_size", executor::getPoolSize).register(Metrics.globalRegistry);
		Gauge.builder( "poolbar_queue_size", executor::getQueueSize).register(Metrics.globalRegistry);
		// find them in /actuator/prometheus
		return executor;
	}

}




