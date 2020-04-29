package victor.training.spring.events.order;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@EnableBinding({Queues.class})
@SpringBootApplication
public class PlaceOrderApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PlaceOrderApp.class, args);
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


    @Autowired
    ApplicationEventPublisher eventPublisher;

    public void run(String... args) {
        placeOrder();
    }

    private void placeOrder() {
        log.debug(">> PERSIST new Order");
        long orderId = 13L;
        eventPublisher.publishEvent(new OrderPlacedEvent(orderId));
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
    public OrderInStockEvent process(OrderPlacedEvent event) {
        log.info("Checking stock for products in order " + event.getOrderId());
        if (stock == 0) {
            throw new IllegalStateException("Out of stock");
        }
        stock--;
        log.info(">> PERSIST new STOCK!!");
        return new OrderInStockEvent(event.getOrderId());
    }
}
@Value
class OrderInStockEvent {
    long orderId;
}

@Slf4j
@Service
class InvoiceService {
    @EventListener
    public void sendInvoice(OrderInStockEvent event) {
        log.info("Generating invoice for order " + event.getOrderId());
        // TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
        log.info(">> PERSIST Invoice!!");
    }
}