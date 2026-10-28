package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
  @Bean
  public Y y(MailService mailService,
             @Value("${props.gate}") Integer gate) {
    Y y = new Y(mailService);
    y.setGate(gate);
    return y;
  }
}
