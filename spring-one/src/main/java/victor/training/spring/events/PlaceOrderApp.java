package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;

import static java.lang.Math.random;

@Slf4j
@EnableAsync
//@EnableBinding({Queues.class})
@SpringBootApplication
@RestController
public class PlaceOrderApp implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(PlaceOrderApp.class, args);
	}

	@GetMapping
	public String method() {
		return "a";
	}

//	@Bean
//	public ApplicationEventMulticaster applicationEventMulticaster() {
//		SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
//		eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
//		return eventMulticaster;
//	}


	// TODO [1] Decouple using @EventListener and ApplicationEventPublisher
	// TODO [2] also generate invoice
	// TODO [2] control the @Order
	// TODO [3] event chaining
	// TODO [4] handle asynchronously: exceptions, Tx, Thread Scope, debate
	// TODO [5] queues: retries, chaining, topics
	// TODO [opt] Transaction-scoped events


	@Autowired
	private OrderService orderService;

	public void run(String... args) {
		orderService.placeOrder();
	}

}
@Service
@Slf4j
class OrderService {
	@Autowired
	private StockManagementService stockManagementService;

//	@Autowired
//	private InvoiceService invoiceService;
@Transactional
	public void placeOrder() {
		log.debug(">> PERSIST new Order");
		long orderId = 13L;
		stockManagementService.process(orderId);
//		invoiceService.sendInvoice(orderId);

		// by default notifies all event listeners right here sync one after the other in this same thread
		eventPublisher.publishEvent(new OrderPlacedEvent(orderId));
		ThreadUtils.sleep(2000);
		log.debug("Finish Place Order usecase");
	}

	@Autowired
	private ApplicationEventPublisher eventPublisher;
}

class OrderPlacedEvent {
	private final long orderId;

	OrderPlacedEvent(long orderId) {
		this.orderId = orderId;
	}

	public long getOrderId() {
		return orderId;
	}
}

@Slf4j
@Service
class StockManagementService {
	private int stock = 3; // silly implem :D

	public void process(long orderId) {
		log.info("Checking stock for products in order " + orderId);
		if (stock == 0) {
			throw new IllegalStateException("Out of stock");
		}
		stock --;
		log.info(">> PERSIST new STOCK!!");
	}
}

@Slf4j
@Service
class InvoiceService {
//	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // send notificationo
//	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION) // resource cleanup
	@Transactional
	@Async
	@EventListener
	public void sendInvoice(OrderPlacedEvent event) {
		long orderId = event.getOrderId();
		log.info("Generating invoice for order " + orderId);
		  if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
		log.info(">> PERSIST Invoice!!");
	}
}
