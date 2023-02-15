package victor.training.spring.integration.beautiful;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

public class Beautiful {
  @Bean

  public IntegrationFlow customErrorFlow(ErrorHandler errorHandler) {
    return IntegrationFlows.from("ERROR_CHANNEL")
            .split()
            .handle(errorHandler)
//            .channel(IntegrationContextUtils.NULL_CHANNEL_BEAN_NAME) // flow ends with a null channel
            .get();
  }
}
