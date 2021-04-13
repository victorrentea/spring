package victor.training.spring.events.stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.CheckStockForOrder;
import victor.training.spring.events.events.OrderIsInStockEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockManagementService {

   private int stock = 2; // silly implem :D

   @EventListener
   public OrderIsInStockEvent process(CheckStockForOrder event) {
      log.info("Checking stock for products in order " + event.getOrderId());
      if (stock == 0) {
         throw new IllegalStateException("Out of stock");
      }
      stock--;
      log.info(">> PERSIST new STOCK!!");
      return new OrderIsInStockEvent(event.getOrderId());
   }
}
