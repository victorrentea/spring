package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvoiceService {
    public void sendInvoice(long orderId) {
        log.info("Generating invoice for order " + orderId);
        // if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
        log.info(">> PERSIST Invoice!!");
    }
}
