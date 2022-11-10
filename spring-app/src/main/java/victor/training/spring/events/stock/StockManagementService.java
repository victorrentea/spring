package victor.training.spring.events.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderPlacedEvent;

@Slf4j
@Service
public class StockManagementService {
   private int stock = 0; // silly implem :D

   @EventListener
   @Order(1)
   public void onOrderPlaced(OrderPlacedEvent event) {
      checkStock(event.getOrderId());
   }

   public void checkStock(long orderId) {
      log.info("Checking stock for products in order " + orderId);
      if (stock == 0) {
         throw new IllegalStateException("Out of stock");
      }
      stock--;
      log.info(">> PERSIST new STOCK!!");
   }
}
