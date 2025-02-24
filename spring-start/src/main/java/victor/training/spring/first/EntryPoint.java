package victor.training.spring.first;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryPoint {
  private static final Logger log = LoggerFactory.getLogger(EntryPoint.class);
  private final ApplicationEventPublisher applicationEventPublisher;

  public EntryPoint(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener(ApplicationStartedEvent.class)
  @Order(7)
  public void start() {
    System.out.println("Hello Spring!");
    CartEvent event = new CartEvent(List.of("Tomatoes", "Cucumber"), 10);
    log.info("Before: " + event);
    applicationEventPublisher.publishEvent(event);
    log.info("After: " +event);
  }
}

class Offer2 {
  private static final Logger log = LoggerFactory.getLogger(Offer2.class);

  @EventListener
  @Order(6) // hard to maintain = global coupling point

  @Async // runs in a separate thread
  // 1) to ignore any errors occurring
  // 2) not wait for a long processing
  public void onCartEvent(CartEvent event) {
    log.info("Offer2: " + event);
    // event handlers run by default in the same thread as publisher, one AFTER THE OTHER in an unspecified order
    // if you buy cucumber and tomatoes -2
    if (event.items.contains("Tomatoes") && event.items.contains("Cucumber")) {
      event.price -= 2; // bad practice; don't show to anyone in Picnic
//      event.appliedOffers.add(my discount(-2, "offer2")) .. better
    }
  }
}

class Offer1 {
  private static final Logger log = LoggerFactory.getLogger(Offer1.class);

  @EventListener
  public void onCartEvent(CartEvent event) {
    log.info("Offer1: " + event);
    if (event.items.contains("Tomatoes")) {
      event.price -= 1;
    }
  }
}

class CartEvent {
  public List<String> items;
  public Integer price;

  public CartEvent(List<String> items, Integer price) {
    this.items = items;
    this.price = price;
  }

  @Override
  public String toString() {
    return "CartEvent{" +
           "items=" + items +
           ", price=" + price +
           '}';
  }
}



// B) Fire a custom CartEvent and have multiple beans listening ( @EventListener )
//    mutating the CartEvent payload
// ✨ Spring MAGIC