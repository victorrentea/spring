package victor.training.spring.integration.cafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import victor.training.spring.integration.cafe.model.OrderItem;

@Slf4j
@Configuration
public class CafeFlowConfig {
  @Autowired
  private Barista barista;

  @Bean
  public MessageChannel orders() {
    return new DirectChannel();
  }

  @Bean
  public IntegrationFlow cafeFlow(OrderSplitter orderSplitter) {
//    IntegrationFlows.from("orders")
    return IntegrationFlows.from(orders())
            .split(orderSplitter)
            .routeToRecipients(route -> route
                    .recipientFlow("payload.isIced()", iceSubFlow())
                    .recipientFlow("! payload.isIced()", hotSubFlow())
//                    .recipientFlow("true", flow->flow.handle(m->log.info("Default also runs: "+m)))
            )
            .get();
  }

  @Bean
  public IntegrationFlow hotSubFlow() {
    return flow->flow
            .handle(OrderItem.class, (payload, headers) -> barista.prepareHotDrink(payload))
//            .handle(waiterSubFlow(null))
            .channel(drinks())
            ;
  }
  @Bean
  public IntegrationFlow iceSubFlow() {
    return flow->flow
            .handle(OrderItem.class, (payload, headers) -> barista.prepareColdDrink(payload))
//            .handle(waiterSubFlow(null))
            .channel(drinks())
            ;
  }
//  @Bean
//  public IntegrationFlow waiterSubFlow(Waiter waiter) {
//    return IntegrationFlows.from(drinks())
//            .aggregate(waiter)
//            .log("GOT")
//            .get();
//  }

  @Bean
  // exercise for the reader: can I avoid this @Bean?
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
