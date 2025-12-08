package victor.training.spring.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class Config2 {
  @Bean
  // a) e clasa dintr-o lib
  // b) vreau sa configurez manual instanta
  public ThreadPoolTaskExecutor executor2() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setMaxPoolSize(5);
    return executor;
  }
  //The bean 'executor', defined in class path resource
  // [victor/training/spring/first/Config2.class], could not be registered.
  // A bean with that name has already been defined in
  // class path resource [victor/training/spring/first/Config.class]
  // and overriding is disabled.

}
