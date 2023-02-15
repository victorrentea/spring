package victor.training.spring.integration.cafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import victor.training.spring.integration.cafe.Cafe;
import victor.training.spring.integration.cafe.model.DrinkType;
import victor.training.spring.integration.cafe.model.Order;

import static java.lang.System.currentTimeMillis;

@Slf4j
@Component
public class Start {
  @Autowired
  Cafe cafe;
//  @EventListener(ApplicationStartedEvent.class)
  public void start() throws InterruptedException {
    log.info("Start!");
    for (int i = 1; i <= 20; i++) {
      Order order = new Order(i);
      order.addItem(DrinkType.LATTE, 2, false);
      order.addItem(DrinkType.MOCHA, 3, true);
      long t0 = currentTimeMillis();
      cafe.placeOrder(order);
      log.info("Submitted order {} in {}ms", order, currentTimeMillis() - t0);
//      Thread.sleep(1000);
    }
  }
}
