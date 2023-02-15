package victor.training.spring.integration.cafe;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import victor.training.spring.integration.cafe.model.Order;

@MessagingGateway
public interface Cafe {

//	@Gateway(requestChannel = "orders")
	void placeOrder(Order order);

}
