package victor.training.spring.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class OrderService  {
	private final StockManagementService stockManagementService;
	private final InvoiceService invoiceService;

	// Chain of responsibility (aka Filters pattern)

	// A) Inject all beans of a type 💖maintainable,traditional
	// @Autowired List<Offer> offers;
	// - @Bean List<Offer> offers() // don't!
	// - inject me *ALL* instances of type Offer in the container
	//    (a) interface Offer {Cart apply(Cart)} + class Offer1 implements Offer + class Offer2 implements Offer
	//    (b) @Bean Offer offer1() {}     @Bean Offer offer2() {}

	// B) Fire a custom CartEvent and have multiple beans listening ( @EventListener )
	//    mutating the CartEvent payload
	// ✨ Spring MAGIC

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