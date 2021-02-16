package victor.training.spring.events.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderPlacedEvent;

@Slf4j
@Service
public class  OrderService {

	@Autowired
	private ApplicationEventPublisher publisher;


	public void placeOrder() {
		log.debug(">> PERSIST new Order");
		long orderId = 13L;
		publisher.publishEvent(new OrderPlacedEvent(orderId));
	}
}
