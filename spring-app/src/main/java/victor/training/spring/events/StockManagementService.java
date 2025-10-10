package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockManagementService {
  private final ApplicationEventPublisher applicationEventPublisher;
  private int stock = 3; // silly implem :D

  public StockManagementService(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener({ApplicationStartedEvent.class, ApplicationReadyEvent.class, ApplicationContextInitializedEvent.class})
  public void meAgain(Object e) {
    System.out.println("Got " + e);
  }

  @EventListener
   public void process(OrderPlaced event) {
      log.info("Checking stock for products in order " + event.orderId());
      if (stock == 0) {
         throw new IllegalStateException("Out of stock");
      }
      stock--;
      log.info(">> PERSIST new STOCK!!");
    applicationEventPublisher.publishEvent(new StockReservedEvent(event.orderId()));
   }
}
