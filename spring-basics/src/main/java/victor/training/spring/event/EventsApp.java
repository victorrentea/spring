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
import org.springframework.core.annotation.Order;
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
@RequiredArgsConstructor
@Service
@Slf4j
class OrderService {
//    private final InvoiceGenerator invoiceGenerator;
    private final ApplicationEventPublisher publisher;

    public void placeOrder(OrderDto orderDto) {
        long newOrderId = 1L;
        log.debug("Creating order " + newOrderId);
//        invoiceGenerator.generateInvoice(newOrderId);
        publisher.publishEvent(new OrderCreatedEvent(newOrderId));
    }
}
class OrderDto {}
@Data
class OrderCreatedEvent {
    private final long orderId;
}
//////////////// alt pachet ////////////////
@Service
@Slf4j
class InvoiceGenerator {
    @EventListener
    @Order(20)
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        generateInvoice(orderCreatedEvent.getOrderId());
    }
    public void generateInvoice(long orderId) {
        log.debug("Generating invoice for order " + orderId);
    }
}

@Service
@Slf4j
class SendConfirmationEmail {
    @Order(10)
    @EventListener
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        generateInvoice(orderCreatedEvent.getOrderId());
    }
    public void generateInvoice(long orderId) {
        if (true) throw new RuntimeException("on purpose");
        log.debug("Sending Confirmation email for order " + orderId);
    }
}