package victor.training.spring.events.order;

import lombok.Value;
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
import org.springframework.core.annotation.Order;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding({Queues.class})
@SpringBootApplication
public class PlaceOrderApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PlaceOrderApp.class, args);
    }

    // TODO [1] Decouple using @EventListener and ApplicationEventPublisher
    // TODO [2] also generate invoice
    // TODO [2] control the @Order
    // TODO [3] event chaining
    // TODO [4] handle asynchronously: exceptions, Tx, Thread Scope, debate
    // TODO [5] queues: retries, chaining, topics
    // TODO [opt] Transaction-scoped events

    @Autowired
    Queues queues;

    public void run(String... args) {
        placeOrder();
    }

    private void placeOrder() {
        log.debug(">> PERSIST new Order");
        long orderId = 13L;
        queues.q1out().send(MessageBuilder.withPayload(orderId + "").build());
    }
}
