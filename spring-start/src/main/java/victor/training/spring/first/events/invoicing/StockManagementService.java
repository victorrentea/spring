package victor.training.spring.first.events.invoicing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import victor.training.spring.first.events.order.OrderPlacedEvent;

@Slf4j
@Service
public class StockManagementService {
   private int stock = 3; // silly implem :D

   @EventListener
   public void onOrderPlaced(OrderPlacedEvent event) {
      log.info("Checking stock for products in order " + event.orderId());
      if (stock == 0) {
         throw new IllegalStateException("Out of stock");
      }
      stock--;
      log.info(">> PERSIST new STOCK!!");
   }

}
