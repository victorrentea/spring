package victor.training.spring.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderService  {
	private final StockManagementService stockManagementService;
	private final InvoiceService invoiceService;

	@EventListener(ApplicationStartedEvent.class)
	public void run() {
		placeOrder();
	}

	private void placeOrder() {
		log.debug(">> PERSIST new Order");
		long orderId = 13L;
		stockManagementService.process(orderId);
		invoiceService.sendInvoice(orderId);
	}
}


// Key Points:
// [1] Send any object via ApplicationEventPublisher to all @EventListener methods
// [2] Control the @Order -> messy code
// [3] Chaining Events
// [4] Spring lifecycle events
// [5] @Async events: exceptions, Transactions, ThreadScope
// [6] (hard) Transaction-scoped events