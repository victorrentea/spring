package victor.training.spring.events.invoicing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderPlacedEvent;

/// ----------- visez ca peste X ani de aici in jos mut in alta aplicatie (microserviciu)
@Slf4j
@Service
public class InvoiceService {

   @Order(2)
   @EventListener
   public void sendInvoice(OrderPlacedEvent event) {
      long orderId = event.getOrderId();
      log.info("Generating invoice for order " + orderId);
      // TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
      log.info(">> PERSIST Invoice!!");
   }
}
