package victor.training.spring.integration.real1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;

@Slf4j
@Configuration
public class XYZSubflowConfiguration {
  public String getExpression() {
    return "payload.contains('OMG')";
  }

  @Bean
  public IntegrationFlow buildSubFlow() {
    return flow -> flow.handle(message -> log.info("got:" + message));
  }
}
