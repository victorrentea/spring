package victor.training.spring.integration.real1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;

@Slf4j
@Configuration
public class DefaultEventSubFlowConfiguration {

  public String getExpression() {
    return "1==1";
  }

  public IntegrationFlow defaultEventSubflow() {
    return flow -> flow.handle(m -> log.info("unhandled-message: " + m));
  }
}
