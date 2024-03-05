package victor.training.spring.first.events.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.events.inventory.InvoiceService;
import victor.training.spring.first.events.invoicing.StockManagementService;

//public record OrderService(StockManagementService )  { // NEVER DO THIS:
// DI works, but record in java17 are FINAL (cannot be subclassed)
// to intercept methods, Spring uses CGLIB to create a subclass of the bean class
@RestController
public class OrderService  {
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);
//	private final StockManagementService stockManagementService;
//	private final InvoiceService invoiceService;
	private final ApplicationEventPublisher eventPublisher;

  public OrderService(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

//	@Transactional // NEVER WORKS if your bean is a record or marked final class.
  @GetMapping("place-order")
	public void placeOrder() {
		log.debug(">> PERSIST new Order");
		long orderId = 13L;
		eventPublisher.publishEvent(new OrderPlacedEvent(orderId));// fired my own event class
		// in microservices => Kafka, RabbitMQ, etc.
		// via spring's internal event bus (in-memory)
//		stockManagementService.process(orderId);
//		invoiceService.sendInvoice(orderId);
	}
}


// Key Points:
// [1] Send any object via ApplicationEventPublisher to all @EventListener methods
// [2] Control the @Order -> messy code
// [3] Chaining Events
// [4] Spring lifecycle events
// [5] @Async events: exceptions, Transactions, ThreadScope
// [6] (hard) Transaction-scoped events