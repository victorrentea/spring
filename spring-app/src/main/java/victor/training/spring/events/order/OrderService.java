package victor.training.spring.events.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import victor.training.spring.events.events.OrderPlacedEvent;
import victor.training.spring.events.invoice.InvoiceService;
import victor.training.spring.events.stock.StockManagementService;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderService  {
	private final ApplicationEventPublisher eventPublisher; // spring

	@EventListener(ApplicationStartedEvent.class)
	public void run() {
		placeOrder();
	}

	private void placeOrder() {
		log.debug(">> PERSIST new Order");
		long orderId = 13L;
		eventPublisher.publishEvent(new OrderPlacedEvent(orderId));//  >> AICI ruleaza spring toate handlerele unu dupa alu
		log.debug("<< END placeOrder");
	}
}


// Key Points:
// [1] Send any object via ApplicationEventPublisher to all @EventListener methods
// [2] Control the @Order -> messy code
// [3] Chaining Events
// [4] Spring lifecycle events
// [5] @Async events: exceptions, Transactions, ThreadScope
// [6] (hard) Transaction-scoped events