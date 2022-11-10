package victor.training.spring.events.stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderInStockEvent;
import victor.training.spring.events.events.OrderPlacedEvent;

@RequiredArgsConstructor
@Slf4j
@Service
public class StockManagementService {
   private int stock = 3; // silly implem :D
private final ApplicationEventPublisher eventPublisher;
   @EventListener
//   @Order(1)
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
      eventPublisher.publishEvent(new OrderInStockEvent(orderId));
   }
}
