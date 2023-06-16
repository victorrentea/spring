package victor.training.spring.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {
  @Bean
  public ThreadPoolTaskExecutor barPool() {
    ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
    threadPool.setMaxPoolSize(2);
    threadPool.setCorePoolSize(2);
    threadPool.setQueueCapacity(500);
    threadPool.setThreadNamePrefix("bar-");
    return threadPool;
  }
}



