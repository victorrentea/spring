package victor.training.spring.async;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {
  @Bean
  @ConfigurationProperties(prefix = "bar.thread.pool")
  public ThreadPoolTaskExecutor barPool() {
    return new ThreadPoolTaskExecutor();
  }
}



