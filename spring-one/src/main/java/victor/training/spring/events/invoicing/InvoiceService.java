package victor.training.spring.events.invoicing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderCreatedEvent;

@Slf4j
@Service
public class InvoiceService {
   @EventListener
   @Order(2)
   public void sendInvoice(OrderCreatedEvent event) {
      log.info("Generating invoice for order " + event.getOrderId());
      // TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
      log.info(">> PERSIST Invoice!!");
   }
}
