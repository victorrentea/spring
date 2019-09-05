package spring.training.events;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
@EnableBinding({Queues.class})
@EnableAsync
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
	private Queues queues;

	// @Transactional
	private void placeOrder() {
		long orderId = new Random().nextInt();
		log.debug(">> PERSIST new Order id " + orderId);

		Message<Long> message = MessageBuilder.withPayload(orderId).build();
//		queues.q1out().send(message);
		log.debug("Finished handling event");
	}
}
@Data
class OrderPlacedEvent {
	private final long orderId;
}
@Slf4j
@MessageEndpoint
class StockManagementService {
	private int stock = 3; // silly implem :D

	// @Transactional
	@ServiceActivator(inputChannel = Queues.Q1_IN)
	public void process(Long orderId) {
//		long orderId = event.getOrderId();
		log.info("Checking stock for products in order " + orderId);
		if (stock == 0) {
			throw new IllegalStateException("Out of stock");
		}
		stock --;
		log.info(">> PERSIST new STOCK!!");
//		return new StockAvailableForOrderEvent(orderId);
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
	@Async
	@EventListener
	public void sendInvoice(StockAvailableForOrderEvent event) {
		long orderId = event.getOrderId();
		log.info("Generating invoice for order " + orderId);
		// TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
		log.info(">> PERSIST Invoice!!");
	}
}