package victor.training.spring.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import victor.training.spring.Y;

@Configuration
public class AltaClasa {
  @Bean
  public Y y() {
    return new Y();
  }
}
