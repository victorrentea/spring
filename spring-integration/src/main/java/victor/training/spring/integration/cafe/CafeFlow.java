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

  @Bean
  public MessageChannel orders() {
    return new DirectChannel();
  }

  @Bean
  public IntegrationFlow ordersFlow(OrderSplitter orderSplitter) {
    return IntegrationFlows.from(orders())
            .split(orderSplitter)
            //.split("payload.items") // SpEL alternative
            .route((OrderItem orderItem) -> orderItem.isIced() ? "coldDrinkOrders" : "hotDrinkOrders")
            .get();
  }

  @Bean
  public PollableChannel coldDrinkOrders() {
    return new QueueChannel(10);
  }

  @Bean
  public PollableChannel hotDrinkOrders() {
    return new QueueChannel(10);
  }

  // TODO vrem ca cele doua canale coldDrinkOrders si hotDrinkOrders sa devina Pollable (tu ii vei cere din treadpoolurile barmanilor)
  //  nu Subscribable (it to push you work)
  @Bean
  public IntegrationFlow processColdDrinks(Barista barista) {
    return IntegrationFlows.from(coldDrinkOrders())
            .bridge(e -> e.poller(Pollers.fixedDelay(1000)
                    .taskExecutor(ExecutorUtils.executor("cold", 2, 100))))
            .handle(barista, "prepareColdDrink")
            .channel(preparedDrinks())
            .get();
  }

  @Bean
  public IntegrationFlow processHotDrinks(Barista barista) {
    return IntegrationFlows.from(hotDrinkOrders())
            .bridge(e -> e.poller(Pollers.fixedDelay(1000)
                    /*.maxMessagesPerPoll(1)*/
                    .taskExecutor(ExecutorUtils.executor("hot", 1, 100))))
            .handle(barista, "prepareHotDrink")
            .channel(preparedDrinks())
            .get();
  }

  @Bean
  public MessageChannel preparedDrinks() {
    return new DirectChannel();
  }

  @Bean
  public MessageChannel deliveries() {
    return new DirectChannel();
  }

  @Bean
  public IntegrationFlow pollDeliveries() {
    return IntegrationFlows.from("deliveries")
            .log(WARN, "order delivered")
            .get();
  }

}
