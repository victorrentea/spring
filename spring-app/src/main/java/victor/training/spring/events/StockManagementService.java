package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockManagementService {
   private int stock = 3; // silly implem :D

  public StockManagementService(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

   @EventListener
   @Order(1)
   public void process(OrderPlacedEvent event) {
      log.info("Checking stock for products in order " + event.orderId());
      if (stock == 0) {
         throw new IllegalStateException("Out of stock");
      }
      stock--;
      log.info(">> PERSIST new STOCK!!");
      // to get rid of the order annotation, what you
      // can do is to fire another intermediary event
      // from the first listener that the second listener
      // is gonna subscribe to instead of the original event
   }
   private final ApplicationEventPublisher eventPublisher;
}
