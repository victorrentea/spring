package victor.training.spring.events.stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderCreatedEvent;
import victor.training.spring.events.events.OrderIsInStockEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockManagementService {

   private int stock = 0; // silly implem :D

   @EventListener
   public OrderIsInStockEvent process(OrderCreatedEvent event) {
      log.info("Checking stock for products in order " + event.getOrderId());
      if (stock == 0) {
         throw new IllegalStateException("Out of stock");
      }
      stock--;
      log.info(">> PERSIST new STOCK!!");
      return new OrderIsInStockEvent(event.getOrderId());
   }
}
