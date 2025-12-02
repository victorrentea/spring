package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ConfigThreadPool {
  //thread-pool.threads: 5
  //thread-pool.queue: 500
//  @Bean
//  public ThreadPoolTaskExecutor threadPool(
//      @Value("${thread-pool.threads}") int threads,
//      @Value("${thread-pool.queue}") int queue
//  ) {
//    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//    executor.setCorePoolSize(threads);
//    executor.setMaxPoolSize(threads);
//    executor.setQueueCapacity(queue);
//    executor.initialize();
//    return executor;
//  }
  @Bean
  @ConfigurationProperties(prefix = "thread-pool")
  public ThreadPoolTaskExecutor threadPool() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.initialize();
    return executor;
  }
}
