package victor.training.spring.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

public class MyConfig {// implements somth from Spring

  @Bean
  public X x() {
    return new X(y());
  }
  @Bean
  public Y y() {
    return new Y();
  }
}