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
import org.springframework.messaging.MessageChannel;
@EnableIntegration
@IntegrationComponentScan("org.springframework.integration.samples.cafe") // sa scaneze si interfete @MessageGateway pt care sa creeze apoi proxyuri
//@ImportResource("/META-INF/spring/integration/cafeDemo-annotation.xml") //inspiration
@ComponentScan
public class CafeDemoConfig {

    @Bean
    public IntegrationFlow logOrders() {
            return IntegrationFlows.from("orders")
                    .log(LoggingHandler.Level.WARN)
                    .get();
    }

}
