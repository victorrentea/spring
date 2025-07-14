package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import victor.training.spring.altu.X;

@Configuration // pt ca contine @Bean
@RequiredArgsConstructor
public class UnConfig {
  @Bean
  public X x(Y y) {
    return new X(y);
  }
}
