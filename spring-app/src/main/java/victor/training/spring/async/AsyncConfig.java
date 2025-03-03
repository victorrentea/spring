package victor.training.spring.async;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@EnableScheduling
@Configuration
public class AsyncConfig {
//	@Bean
//	public ThreadPoolTaskExecutor poolBar(@Value("${bar.pool.size}") int size) {
	@Bean
	@ConfigurationProperties(prefix = "bar.pool")
	public ThreadPoolTaskExecutor poolBar() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(size);
//		executor.setMaxPoolSize(size); // # of threads
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("pool-bar-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true); // !!!! must-have!

		// copy MDC from parent thread to worker thread (eg: traceId)
		executor.setTaskDecorator(new CopyMDCToWorker());

		Gauge.builder( "poolbar_pool_size2", executor::getPoolSize).register(Metrics.globalRegistry);
		Gauge.builder( "poolbar_queue_size", executor::getQueueSize).register(Metrics.globalRegistry);
		// Usges:
		// - raise alarm if queue size > 400
		// - the instance could declare itself readyness => false
		return executor;
	}

}




