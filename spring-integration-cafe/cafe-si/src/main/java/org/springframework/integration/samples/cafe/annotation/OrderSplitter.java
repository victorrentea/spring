package org.springframework.integration.samples.cafe.annotation;

import java.util.List;

import org.springframework.integration.samples.cafe.Order;
import org.springframework.integration.samples.cafe.OrderItem;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;

@MessageEndpoint
public class OrderSplitter {

	@Splitter(inputChannel="orders", outputChannel="drinks")
	public List<OrderItem> split(Order order) {
		return order.getItems();
	}

}
