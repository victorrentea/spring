package victor.training.spring.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

public class MyConfig {// implements somth from Spring
  @Bean
  public X x() {
    // NPE because Spring did not process the @AUtowired, @Value... annotation in Y
    // because the call on the next line
    // is not seen a reference to a spring Bean,
    // but just the call of a regular method
    return new X(y());
  }
  @Bean
  public Y y() {
    return new Y();
  }
}