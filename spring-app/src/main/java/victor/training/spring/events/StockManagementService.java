package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@Scope("singleton") // default. si nu ar trebui altceva
public class StockManagementService {
   private int stock = 3; // silly implem :D
  // NU TE PRIND cu state neprotejat anti-concurrency
  // in beanuri singletoane Spring

   @EventListener // IoC
   @Order(10)
   public void process(OrderPlacedEvent event) {
      log.info("Checking stock for products in order " + event.orderId());
      if (stock == 0) {
         throw new IllegalStateException("Out of stock");
      }
      stock--;
      log.info(">> PERSIST new STOCK!!");
   }
}
