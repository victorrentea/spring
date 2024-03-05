package victor.training.spring.first.events.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.first.events.invoicing.StockReservedEvent;
import victor.training.spring.first.events.order.OrderPlacedEvent;

@Slf4j
@Service
public class InvoiceService {
  @EventListener
//   @Order(20) // order of the listeners should never be important
  public void onOrderPlaced(StockReservedEvent event) {
    log.info("Generating invoice for order " + event.orderId());
    // if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
    log.info(">> PERSIST Invoice!!");
  }

  @EventListener
  @Async // makes the caller not wait for the listener to finish.
  // also, any exception thrown here will be not visible to the caller
  public void longRunningFlow(OrderPlacedEvent event) {
    // heavy CPU
//    sending emails
  }
}

// avoid events, they make the code harder to navigate/reason about

// events should be immutable

// events are published synchronously to all listeners
