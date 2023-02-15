package victor.training.spring.integration.cafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.messaging.MessageChannel;
import victor.training.spring.integration.cafe.model.OrderItem;

@Slf4j
@Configuration
public class CafeFlowConfig {

  @Bean
  public MessageChannel orders() {
    return new DirectChannel();
  }

  @Bean
  public IntegrationFlow cafeFlow(OrderSplitter orderSplitter) {
//    IntegrationFlows.from("orders")
    return IntegrationFlows.from(orders())
            .split(orderSplitter)
            .route((OrderItem orderItem) -> orderItem.isIced()?"coldDrinkOrders":"hotDrinkOrders")
            .get();
  }
  @Bean
  public MessageChannel coldDrinkOrders() {
    return new DirectChannel();
  }
  @Bean
  public MessageChannel hotDrinkOrders() {
    return new DirectChannel();
  }

  @Bean
  public StandardIntegrationFlow hotFlow() {
    return IntegrationFlows.from(hotDrinkOrders())
            .handle(m -> log.info("HOT: " + m))
            .get();
  }
  @Bean
  public StandardIntegrationFlow coldFlow() {
    return IntegrationFlows.from(coldDrinkOrders())
            .handle(m -> log.info("COLD: " + m))
            .get();
  }

}
