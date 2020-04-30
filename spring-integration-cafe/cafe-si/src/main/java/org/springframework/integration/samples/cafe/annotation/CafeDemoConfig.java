package org.springframework.integration.samples.cafe.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.samples.cafe.OrderItem;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

@EnableIntegration
@IntegrationComponentScan("org.springframework.integration.samples.cafe") // sa scaneze si interfete @MessageGateway pt care sa creeze apoi proxyuri
//@ImportResource("/META-INF/spring/integration/cafeDemo-annotation.xml") //inspiration
@ComponentScan
public class CafeDemoConfig {

    @Autowired
    private Barista barista;

    @Bean
    public IntegrationFlow logOrders() {
            return IntegrationFlows.from("orders")
//                    .split("orderSplitter", "split")
                    .split("payload.items") // SpEL   -> payload este un atribut al GenericMessage (vezi log)
                    .route((OrderItem orderItem) -> orderItem.isIced()? "coldDrinks" : "hotDrinks")
                    .get();
    }

    @Bean
    public PollableChannel coldDrinks() {
        return new QueueChannel(10);
    }
    @Bean
    public PollableChannel hotDrinks() {
        return new QueueChannel(10);
    }

    // TODO vrem ca cele doua canale coldDrinks si hotDrinks sa devina Pollable (tu ii vei cere din treadpoolurile barmanilor)
    //  nu Subscribable (sa dea el in tine ca animalu)
    @Bean
    public IntegrationFlow processColdDrinks() {
        return IntegrationFlows.from("coldDrinks")
                .bridge(e -> e.poller(Pollers.fixedDelay(1000)))
                .handle(barista, "prepareColdDrink")
                .log(LoggingHandler.Level.WARN, "cold")
                .channel("drinks")
                .get();
    }
    @Bean
    public IntegrationFlow processHotDrinks() {
        return IntegrationFlows.from("hotDrinks")
                .bridge(e -> e.poller(Pollers.fixedDelay(1000)/*.maxMessagesPerPoll(1)*/))
                .handle(barista, "prepareHotDrink")
                .log(LoggingHandler.Level.WARN, "hot")
                .channel("drinks")
                .get();
    }

    @Bean
    public IntegrationFlow pollDeliveries() {
        return IntegrationFlows.from("deliveries")
                .log(LoggingHandler.Level.WARN, "delivery")
                .get();
    }
}
