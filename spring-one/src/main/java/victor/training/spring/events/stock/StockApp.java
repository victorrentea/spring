package victor.training.spring.events.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@EnableBinding({Queues.class})
@SpringBootApplication
public class StockApp {
    public static void main(String[] args) {
        SpringApplication.run(StockApp.class, args);
    }

}

@Slf4j
@MessageEndpoint
class StockManagementService {
    private int stock = 3; // silly implem :D

    @Autowired
    private Queues queues;

    // TODO exercitiu cititorului
//    @Transactional(propagation = Propagation.MANDATORY)
    @ServiceActivator(inputChannel = Queues.Q1_IN)
    public void process(String orderIdStr) {
        log.info("Checking stock for products in order " + orderIdStr);
        if (stock == 0) {
            throw new IllegalStateException("Out of stock");
        }
        stock--;
        log.info(">> PERSIST new STOCK!!");
//        return new OrderInStockEvent(event.getOrderId());
        queues.q2out().send(MessageBuilder.withPayload(orderIdStr + " kiki").build());
    }
}
