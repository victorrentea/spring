package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockManagementService {
  private int stock = 0; // silly implem :D

  @EventListener
  @Order(50)
//  @Async😱
  public void process(OrderPlacedEvent event) {
    log.info("Checking stock for products in order " + event.orderId());
    if (stock == 0) {
      throw new IllegalStateException("Out of stock");
    }
    stock--;
    log.info(">> PERSIST new STOCK!!");
    // to avoid global @oRder of EventListeners, you can chain events
    // applicationEventPublisher.publishEvent(new OrderStockupEvent(orderId))
  }
}
