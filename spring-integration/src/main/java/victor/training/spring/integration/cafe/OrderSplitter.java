package victor.training.spring.integration.cafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import victor.training.spring.integration.cafe.model.Order;
import victor.training.spring.integration.cafe.model.OrderItem;

import java.util.List;

@Slf4j
@MessageEndpoint
public class OrderSplitter {
	public List<OrderItem> split(Order order) {
		log.info("Splitting order: " + order + " -> " + order.getItems());
		return order.getItems();
	}

}
