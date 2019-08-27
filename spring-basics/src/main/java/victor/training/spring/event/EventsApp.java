package victor.training.spring.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class EventsApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(EventsApp.class,args);
    }

//	@Bean
//    public ApplicationEventMulticaster applicationEventMulticaster() {
//        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
//        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
//        return eventMulticaster;
//    }

    @Autowired
    private OrderService orderService;
    @Override
    public void run(String... args) throws Exception {
        orderService.placeOrder(new OrderDto());
    }
}
class OrderDto {}
@RequiredArgsConstructor
@Service
@Slf4j
class OrderService {
    private final ApplicationEventPublisher publisher;

    public void placeOrder(OrderDto orderDto) {
        long newOrderId = 1L;
        log.debug("Creating order " + newOrderId);
        publisher.publishEvent(new OrderCreatedEvent(newOrderId));
    }
}
@Data
class OrderCreatedEvent {
    private final long orderId;
}
//////////////// alt pachet ////////////////
@Service
@Slf4j
class InvoiceGenerator {
    @EventListener
    public InvoiceGeneratedEvent handle(OrderCreatedEvent orderCreatedEvent) {
        // persist Invoice in DB
        log.debug("Generating invoice for order " + orderCreatedEvent.getOrderId());
        return new InvoiceGeneratedEvent(orderCreatedEvent.getOrderId());
    }
}
@Data
class InvoiceGeneratedEvent {
    private final long orderId;
}
@Service
@Slf4j
class SendConfirmationEmail {
    @EventListener
    public void handle(InvoiceGeneratedEvent orderCreatedEvent) {
        log.debug("Sending Confirmation email with invoice din DB for order " +
                orderCreatedEvent.getOrderId());
    }
}

