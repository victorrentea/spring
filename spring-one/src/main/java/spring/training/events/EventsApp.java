package spring.training.events;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@EnableBinding({})
@SpringBootApplication
public class EventsApp implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(EventsApp.class, args);
	}
	
	@Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }


	// TODO [1] Decouple using @EventListener and ApplicationEventPublisher
	// TODO [2] also generate invoice
	// TODO [2] control the @Order
	// TODO [3] event chaining
	// TODO [4] handle asynchronously: exceptions, Tx, Thread Scope, debate
	// TODO [5] queues: retries, chaining, topics
	// TODO [opt] Transaction-scoped events


	public void run(String... args) throws Exception {
		placeOrder();
	}


	@Autowired
	private ApplicationEventPublisher publisher;

	// @Transactional
	private void placeOrder() {
		log.debug(">> PERSIST new Order");
		long orderId = 13L;

		publisher.publishEvent(new OrderPlacedEvent(orderId));
		log.debug("Finished handling event");
	}
}
@Data
class OrderPlacedEvent {
	private final long orderId;
}
@Slf4j
@Service
class StockManagementService {
	private int stock = 0; // silly implem :D

	// @Transactional
	@EventListener
	public StockAvailableForOrderEvent process(OrderPlacedEvent event) {
		long orderId = event.getOrderId();
		log.info("Checking stock for products in order " + orderId);
		if (stock == 0) {
			throw new IllegalStateException("Out of stock");
		}
		stock --;
		log.info(">> PERSIST new STOCK!!");
		return new StockAvailableForOrderEvent(orderId);
	}
}
@Data
class StockAvailableForOrderEvent {
	private final long orderId;
}

@Slf4j
@Service
class InvoiceService {
	// @Transactional
	@EventListener
	public void sendInvoice(StockAvailableForOrderEvent event) {
		long orderId = event.getOrderId();
		log.info("Generating invoice for order " + orderId);
		// TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
		log.info(">> PERSIST Invoice!!");
	}
}