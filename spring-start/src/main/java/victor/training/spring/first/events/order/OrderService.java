package victor.training.spring.first.events.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.events.inventory.InvoiceService;
import victor.training.spring.first.events.invoicing.StockManagementService;

@RequiredArgsConstructor
@Slf4j
@RestController
public class OrderService  {
	private final StockManagementService stockManagementService;
	private final InvoiceService invoiceService;

	@GetMapping("place-order")
	public void placeOrder() {
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