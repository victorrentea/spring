package victor.training.spring.events.invoicing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@Slf4j
@EnableBinding({Queues.class})
@SpringBootApplication
public class InvoicingApp {
    public static void main(String[] args) {
        SpringApplication.run(InvoicingApp.class, args);
    }

}

@Slf4j
@MessageEndpoint
class InvoiceService {
    @ServiceActivator(inputChannel = Queues.Q2_IN)
    public void process(String orderIdStr) {
        log.info("Generating invoice for order " + orderIdStr);
        // TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
        log.info(">> PERSIST Invoice!!");
    }
}
