package victor.training.spring.events.order;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@EnableBinding({Queues.class})
@SpringBootApplication
public class PlaceOrderApp implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(PlaceOrderApp.class, args);
	}
	
//	@Bean
//    public ApplicationEventMulticaster applicationEventMulticaster() {
//        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
//        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
//        return eventMulticaster;
//    }


	// TODO [1] Decouple using @EventListener and ApplicationEventPublisher
	// TODO [2] also generate invoice
	// TODO [2] control the @Order
	// TODO [3] event chaining
	// TODO [4] handle asynchronously: exceptions, Tx, Thread Scope, debate
	// TODO [5] queues: retries, chaining, topics
	// TODO [opt] Transaction-scoped events


	public void run(String... args) {
		placeOrder();
	}

	@Autowired
	ApplicationEventPublisher publisher;
	//  package order;
	private void placeOrder() {
		log.debug(">> PERSIST new Order");
		long orderId = 13L;
		// topic/ exchange
		publisher.publishEvent(new OrderPlacedEvent(orderId)); // un EVENT // un raport indiferent, many possible (unknown) listeners

//		getListenersForEvent(event.getClass()) : List<metode de chema> < gasite la scanarea de compoenente,
//		ca sunt metode publice marcate cu @EventListenr

		// queue 1:1
//		publisher.publishEvent(new CheckStockCommand(orderId)); // un COMMAND: 1 listener, o actiune de facut
	}
}

@Data
class OrderPlacedEvent {
	private final long orderId;
}

// package .. stock;
@Slf4j
@Service
class StockManagementService {
	@Autowired
	private ApplicationEventPublisher publisher;
	private int stock = 3; // silly implem :D

	@EventListener
	public void process(OrderPlacedEvent orderPlacedEvent) {
		log.info("Checking stock for products in order " + orderPlacedEvent.getOrderId());
		if (stock == 0) {
			throw new IllegalStateException("Out of stock");
		}
		stock --;
		log.info(">> PERSIST new STOCK!!");
		publisher.publishEvent(new InvoiceOrderCommand(orderPlacedEvent.getOrderId()));
	}
}
@Data
class InvoiceOrderCommand {
	private final long orderId;
}

// package .. invoicing;
@Slf4j
@Service
class InvoiceService {
	@EventListener
	public void sendInvoice(InvoiceOrderCommand command) {
		log.info("Generating invoice for order " + command.getOrderId());
		// TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
		// tu debughezi aici va trebui sa te uiti la correlationId header de pe
		// incoming message pentru a vedea de unde a venit napasta.
		log.info(">> PERSIST Invoice!!");
	}
}