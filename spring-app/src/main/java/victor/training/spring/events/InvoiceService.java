package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvoiceService {
  @EventListener
  @Order(100)
//  @Async😱
  public void sendInvoice(OrderPlacedEvent event) { // OrderStockupEvent
    log.info("Generating invoice for order " + event.orderId());
    // if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
    log.info(">> PERSIST Invoice!!");
  }
}
