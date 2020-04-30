package org.springframework.integration.samples.cafe.annotation;

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
import org.springframework.messaging.MessageChannel;

/**
 * Provides the 'main' method for running the Cafe Demo application. When an
 * order is placed, the Cafe will send that order to the "orders" channel.
 * The channels are defined within the configuration file ("cafeDemo.xml"),
 * and the relevant components are configured with annotations (such as the
 * OrderSplitter, DrinkRouter, and Barista classes).
 *
 * @author Mark Fisher
 * @author Marius Bogoevici
 */
@EnableIntegration
@IntegrationComponentScan("org.springframework.integration.samples.cafe")
@ImportResource("/META-INF/spring/integration/cafeDemo-annotation.xml")
@ComponentScan("org.springframework.integration.samples.cafe.annotation")
public class CafeDemoConfig {

    @Bean
    public MessageChannel orders() {
        return new DirectChannel();
    }
    @Bean
    public MessageChannel coldDrinks() {
        return new QueueChannel(10);
    }

    @Bean
    public IntegrationFlow bridgeCold() {
        return IntegrationFlows.from("coldDrinks")
                .bridge(e -> e.poller(Pollers.fixedDelay(1000)))
                .channel("coldDrinkBarista")
                .get();
    }

    @Bean
    public IntegrationFlow printout() {
        return IntegrationFlows.from("deliveries")
                .log(LoggingHandler.Level.WARN)
                .get();
    }

}
