package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvoiceService {
  @Autowired
  @Lazy
  private OrderService orderService;
  @EventListener
  @Order(20)
  @Async // I had a problem and I thought of using multithreading.
  // have I two problems now. = RACE BUG
  public void sendInvoice(OrderPlacedEvent event) {
    log.info("Dep inversa din ciclu nu poate fi obiectul real OrderService, pt ca nu e gata inca: " + orderService.getClass());
    log.info("Generating invoice for order " + event.orderId());
    // if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
    log.info(">> PERSIST Invoice!!");
  }
}
