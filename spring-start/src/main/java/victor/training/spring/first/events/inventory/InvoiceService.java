package victor.training.spring.first.events.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import victor.training.spring.first.events.order.OrderPlacedEvent;

@Slf4j
@Service
public class InvoiceService {
   @EventListener
   public void onOrderPlaced(OrderPlacedEvent event) {
      log.info("Generating invoice for order " + event.orderId());
      // if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
      log.info(">> PERSIST Invoice!!");
   }
}
