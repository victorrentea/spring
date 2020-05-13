package org.springframework.integration.samples.cafe.annotation;

import java.util.List;

import org.springframework.integration.samples.cafe.Delivery;
import org.springframework.integration.samples.cafe.Drink;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.stereotype.Component;

@Component
public class Waiter {

	@Aggregator(inputChannel = "drinks", outputChannel = "deliveries")
	public Delivery prepareDelivery(List<Drink> drinks) {
		return new Delivery(drinks);
	}
	@CorrelationStrategy
	public int getCorrelationKey(Drink drink) {
		return drink.getOrderNumber();
	}

}
