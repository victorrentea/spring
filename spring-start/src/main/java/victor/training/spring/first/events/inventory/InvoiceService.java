package victor.training.spring.first.events.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import victor.training.spring.first.events.invoicing.StockReservedEvent;
import victor.training.spring.first.events.order.OrderPlacedEvent;

@Slf4j
@Service
public class InvoiceService {
   @EventListener
//   @Order(20) // order of the listeners should never be important
   public void onOrderPlaced(StockReservedEvent event) {
      log.info("Generating invoice for order " + event.orderId());
      // if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
      log.info(">> PERSIST Invoice!!");
   }
}

// avoid events, they make the code harder to navigate/reason about

// events should be immutable

// events are published synchronously to all listeners
