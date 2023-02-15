package victor.training.spring.integration.cafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import victor.training.spring.integration.ExecutorUtils;
import victor.training.spring.integration.cafe.model.OrderItem;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
public class CafeFlowConfig {
  @Autowired
  private Barista barista;

  @Bean
  public MessageChannel orders() {
//    return new PublishSubscribeChannel();
//    return new QueueChannel(10);
    return new DirectChannel();
  }

  @Bean
  public IntegrationFlow cafeFlow(OrderSplitter orderSplitter) {
//    IntegrationFlows.from("orders")
    return IntegrationFlows.from(orders())
//            .split(orderSplitter) // or
            .split("payload.getItems()")
            .routeToRecipients(route -> route
                    .recipientFlow("payload.isIced()", iceSubFlow())
                    .recipientFlow("! payload.isIced()", hotSubFlow())
//                    .recipientFlow("true", flow->flow.handle(m->log.info("Default also runs: "+m)))
            )
            .get();
  }

  public IntegrationFlow hotSubFlow() {
    return flow->flow
            .channel(MessageChannels.executor(hotExecutor()))
            .handle(OrderItem.class, (payload, headers) -> barista.prepareHotDrink(payload))
            .channel(drinks());
  }

  private Executor hotExecutor() {
    return ExecutorUtils.executor("hot", 1, 100);
  }

  public IntegrationFlow iceSubFlow() {
    ThreadPoolTaskExecutor one = iceExecutor();
    ThreadPoolTaskExecutor two = iceExecutor();
    return flow->flow
            .channel(MessageChannels.executor(iceExecutor()))
            .handle(OrderItem.class, (payload, headers) -> barista.prepareColdDrink(payload))
            .channel(drinks());
  }

  @Bean // how the hack!? < local method calls -> @Bean method are PROXIED !!! (the only place in spring when this happens!)
  public ThreadPoolTaskExecutor iceExecutor() {
    return ExecutorUtils.executor("ice", 2, 100);
  }

  @Bean // exercise for the reader: can I avoid this @Bean?
  public DirectChannel drinks() {
    return new DirectChannel();
  }

  @Bean
  public IntegrationFlow waiterFlow(Waiter waiter) {
    return IntegrationFlows.from(drinks())
            .aggregate(waiter)
            .log("GOT")
            .get();
  }
}
