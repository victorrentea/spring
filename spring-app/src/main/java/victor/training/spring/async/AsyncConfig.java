package victor.training.spring.async;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
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
  @ConfigurationProperties(prefix = "custom-executor")
	public ThreadPoolTaskExecutor poolBar(
      /*@Value("${custom-executor.core-pool-size}") int no*/) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// copy MDC from parent thread to workerthread
		executor.setTaskDecorator(new CopyMDCToWorker());

		Gauge.builder( "poolbar_pool_size", executor::getPoolSize).register(Metrics.globalRegistry);
		Gauge.builder( "poolbar_queue_size", executor::getQueueSize).register(Metrics.globalRegistry);
		// find them in /actuator/prometheus
		return executor;
	}

}




