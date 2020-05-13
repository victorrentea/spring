package org.springframework.integration.samples.cafe;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface Cafe {

	@Gateway(requestChannel = "orders")
	void placeOrder(Order order);

}
