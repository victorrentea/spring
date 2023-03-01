package victor.training.spring.events.invoice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderPlacedEvent;

@Slf4j
@Service
public class InvoiceService {

   @Order(2)
   @EventListener
   public void onOPE(OrderPlacedEvent event) {
      sendInvoice(event.getOrderId());
   }

   public void sendInvoice(long orderId) {
      log.info("Generating invoice for order " + orderId);
      // if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
      log.info(">> PERSIST Invoice!!");
   }
}



//   temporal coupling between the handlers =>
//        1) @Order -> dark => central couplign point
//        2) Event chaining = fire another (2nd) event from stock -> invoicing