package victor.training.spring.events.invoice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderPlacedEvent;

@Slf4j
@Service
public class InvoiceService {
   @EventListener
   public void onOrderPlacedEvent(OrderPlacedEvent event) {
      sendInvoice(event.getOrderId());
   }
   public void sendInvoice(long orderId) {
      log.info("Generating invoice for order " + orderId);
      // if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
      log.info(">> PERSIST Invoice!!");
   }
}
