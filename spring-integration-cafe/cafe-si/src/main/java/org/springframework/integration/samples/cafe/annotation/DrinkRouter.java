package org.springframework.integration.samples.cafe.annotation;

import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.samples.cafe.OrderItem;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

@MessageEndpoint
public class DrinkRouter {

	@Router(inputChannel="drinks")
	public String resolveOrderItemChannel(OrderItem orderItem) {
		return (orderItem.isIced()) ? "coldDrinks" : "hotDrinks";
	}

}
