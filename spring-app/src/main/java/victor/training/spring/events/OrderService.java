package victor.training.spring.events;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class OrderService implements CommandLineRunner {
	private final StockManagementService stockManagementService;
	private final InvoiceService invoiceService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("place-order")
//    CommandLineRunner //2 joburi sa vezi command line args
//    @PostConstruct //1 per-bean
    @EventListener(ApplicationStartedEvent.class)// 3
//    @Transactional
	public void placeOrder() {
		log.debug(">> PERSIST new Order");
//        repo.save()// @Transactional nu vor merge in @PostConstruct
		long orderId = 13L;
//		stockManagementService.process(orderId);
//		invoiceService.sendInvoice(orderId);
        applicationEventPublisher.publishEvent(new OrderPlacedEvent(orderId));
	}

    @Override
    public void run(String... args) throws Exception { // pt batchuri / joburi
    }
}


// Key Points:
// [1] Send any object via ApplicationEventPublisher to all @EventListener methods
// [2] Control the @Order -> messy code
// [3] Chaining Events
// [4] Spring lifecycle events
// [5] @Async events: exceptions, Transactions, ThreadScope
// [6] (hard) Transaction-scoped events