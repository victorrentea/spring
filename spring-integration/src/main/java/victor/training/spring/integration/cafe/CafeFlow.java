package victor.training.spring.integration.cafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import victor.training.spring.integration.ExecutorUtils;
import victor.training.spring.integration.cafe.model.OrderItem;

import static org.springframework.integration.handler.LoggingHandler.Level.WARN;

@Slf4j
@Configuration
public class CafeFlow {


}
