package victor.training.spring.async;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
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
  @ConfigurationProperties("pool.bar")
  public ThreadPoolTaskExecutor poolBar(TaskDecorator taskDecorator) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setWaitForTasksToCompleteOnShutdown(true);
    // virtual threads
//    executor.setThreadFactory(Thread.ofVirtual().name("pool-bar-", 0).factory());
    executor.setTaskDecorator(taskDecorator); // decorator setat NEAPARAT inainte de initialize()
    executor.initialize();

    Gauge.builder("poolbar_pool_size", executor::getPoolSize).register(Metrics.globalRegistry);
    Gauge.builder("poolbar_queue_size", executor::getQueueSize).register(Metrics.globalRegistry);
    // TODO find these metrics in /actuator/prometheus
    return executor;
  }
}




