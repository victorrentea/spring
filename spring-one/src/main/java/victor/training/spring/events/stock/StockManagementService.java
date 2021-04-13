package victor.training.spring.events.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderCreatedEvent;

@Slf4j
@Service
public class StockManagementService {
   private int stock = 3; // silly implem :D

   @EventListener
   @Order(1)
   public void process(OrderCreatedEvent event) {
      log.info("Checking stock for products in order " + event.getOrderId());
      if (stock == 0) {
         throw new IllegalStateException("Out of stock");
      }
      stock--;
      log.info(">> PERSIST new STOCK!!");
   }
}
