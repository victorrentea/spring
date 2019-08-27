package victor.training.spring.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import victor.training.spring.ThreadUtils;

@SpringBootApplication
@EnableBinding({Queues.class})
public class EventsApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(EventsApp.class, args);
    }

    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        orderService.placeOrder(new OrderDto());
    }
}

class OrderDto {
}

@RequiredArgsConstructor
@Service
@Slf4j
class OrderService {
    private final ApplicationEventPublisher publisher;

    public void placeOrder(OrderDto orderDto) {
        for (int i = 0; i < 10; i++) {
            log.debug("Creating order " + i);
            publisher.publishEvent(new OrderCreatedEvent(i));

        }
    }
}

@Data
class OrderCreatedEvent {
    private final long orderId;
}

//////////////// alt pachet ////////////////
@Service
@Slf4j
@RequiredArgsConstructor
class InvoiceGenerator {
    private final Queues source;

    @EventListener
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        // persist Invoice in DB
//        log.debug("Generating invoice for order " + orderCreatedEvent.getOrderId());
        Message<Long> message = MessageBuilder
                .withPayload(orderCreatedEvent.getOrderId())
                .build();
        source.q1out().send(message);
    }
}

@Data
class InvoiceGeneratedEvent {
    private final long orderId;
}

@MessageEndpoint
@Slf4j
class SendConfirmationEmail {
    @Autowired
    private Queues queues;

    @ServiceActivator(inputChannel = Queues.Q1_IN)
    public void handle(Long orderId) {

        log.debug("Sending Confirmation email with invoice din DB for order " + orderId);
        ThreadUtils.sleep(1000);
        if (Math.random() < .3f) {
            log.error("BUBA" + orderId);
            throw new IllegalArgumentException("Buba");
        }
        log.debug("End " + orderId);
        queues.q2out().send(MessageBuilder.withPayload("invoice"+orderId).build());
    }
}


interface Queues {
    String Q1_OUT = "q1out";
    @Output(Q1_OUT)
    MessageChannel q1out();

    String Q1_IN = "q1in";
    @Input(Q1_IN)
    SubscribableChannel q1in();

    String Q2_OUT = "q2out";
    @Output(Q2_OUT)
    MessageChannel q2out();

    String Q2_IN = "q2in";
    @Input(Q2_IN)
    SubscribableChannel q2in();
}

@MessageEndpoint
@Slf4j
class Compo2 {
    @ServiceActivator(inputChannel = Queues.Q2_IN)
    public void handle(String str) {
        log.debug("Finally got " + str);
    }
}
@MessageEndpoint
@Slf4j
class Compo2Hack {
    @ServiceActivator(inputChannel = Queues.Q1_IN)
    public void handle(Long str) {
        log.debug("Intercept got " + str);
    }
}

