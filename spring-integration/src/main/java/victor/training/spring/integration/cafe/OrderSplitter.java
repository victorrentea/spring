package victor.training.spring.integration.cafe;

import org.springframework.integration.annotation.MessageEndpoint;
import victor.training.spring.integration.cafe.model.Order;
import victor.training.spring.integration.cafe.model.OrderItem;

import java.util.List;

@MessageEndpoint
public class OrderSplitter {
	public List<OrderItem> split(Order order) {
		System.out.println("Splitting order: " + order + " -> " + order.getItems());
		return order.getItems();
	}

}
