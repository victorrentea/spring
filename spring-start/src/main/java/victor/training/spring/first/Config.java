package victor.training.spring.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean // defineste un bean cu numele "y"
  public Y y() {
    return new Y();
  }
}
