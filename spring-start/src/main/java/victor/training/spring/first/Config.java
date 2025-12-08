package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// a) e clasa dintr-o lib
// b) vreau sa configurez manual instanta
@Configuration
public class Config {
  @Bean
  @ConfigurationProperties(prefix = "executor")
  public ThreadPoolTaskExecutor executor(
//      @Value("${executor.threads}") int threads
  ) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//    executor.setMaxPoolSize(threads);
//    executor.setThreadNamePrefix("worker");
    return executor;
  }
}
