package spring.training.events;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@EnableBinding({})
@SpringBootApplication
public class EventsApp implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(EventsApp.class, args);
	}
	
//	@Bean
//    public ApplicationEventMulticaster applicationEventMulticaster() {
//        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
//        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
//        return eventMulticaster;
//    }


	@Autowired
	private ApplicationEventPublisher publisher;

	public void run(String... args) {
		placeOrder();
	}
	private void placeOrder() {
		log.debug(">> PERSIST new Order");
		long orderId = 13L;
		publisher.publishEvent(new OrderPlacedEvent(orderId));
	}
}
@Value
class OrderPlacedEvent {
	long orderId;
}
@Slf4j
@Service
class StockManagementService {
	private int stock = 3; // silly implem :D
	@EventListener
	@Order(10)
	public void handleOrderPlaced(OrderPlacedEvent event) {
		log.info("Checking stock for products in order " + event.getOrderId());
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
	@EventListener
	@Order(20)
	public void sendInvoice(OrderPlacedEvent event) {
		log.info("Generating invoice for order " + event.getOrderId());
		// TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
		log.info(">> PERSIST Invoice!!");
	}
}