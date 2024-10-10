package victor.training.spring.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("order")
public class OrderService  {
	private final ApplicationEventPublisher eventPublisher;

  public OrderService(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  @GetMapping("place-order")
	public String placeOrder() {
		log.debug(">> PERSIST new Order");
		long orderId = 13L;

		// by defaul an exception in any @EventListener will stop the event chain and throw an exception at next line
		eventPublisher.publishEvent(new OrderPlacedEvent(orderId));

		//instead of ...
//		stockManagementService.process(orderId);
//		invoiceService.sendInvoice(orderId);
		return "ok";
	}
}


// Key Points:
// [1] Send any object via ApplicationEventPublisher to all @EventListener methods
// [2] Control the @Order -> messy code
// [3] Chaining Events
// [4] Spring lifecycle events
// [5] @Async events: exceptions, Transactions, ThreadScope
// [6] (hard) Transaction-scoped events