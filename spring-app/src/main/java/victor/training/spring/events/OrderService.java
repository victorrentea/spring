package victor.training.spring.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderService {
  private static final Logger log = LoggerFactory.getLogger(OrderService.class);
  private final ApplicationEventPublisher applicationEventPublisher;

  public OrderService(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @GetMapping("place-order")
  public void placeOrder() throws InterruptedException {
    log.debug(">> PERSIST new Order");
    long orderId = 13L;
    var event = new OrderPlacedEvent(orderId);
    applicationEventPublisher.publishEvent(event);
    Thread.sleep(1000);
    log.debug("DONE");
    //   stockManagementService.process(orderId);
    //    invoiceService.sendInvoice(orderId);
  }

  //  @PostConstruct
  @EventListener
  public void onStartup(ApplicationStartedEvent event) {
    System.out.println("Startup!⭐️⭐️⭐️");
  }
}


// Key Points:
// [1] Send any object via ApplicationEventPublisher to all @EventListener methods
// [2] Control the @Order -> messy code
// [3] Chaining Events
// [4] Spring lifecycle events
// [5] @Async events: exceptions, Transactions, ThreadScope
// [6] (hard) Transaction-scoped events