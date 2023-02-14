package victor.training.spring.integration.cafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import victor.training.spring.integration.cafe.Cafe;
import victor.training.spring.integration.cafe.model.DrinkType;
import victor.training.spring.integration.cafe.model.Order;

@Slf4j
@Component
public class Start {
  @Autowired
  Cafe cafe;
//  @EventListener(ApplicationStartedEvent.class)
  public void start() {
    log.info("Start!");
    for (int i = 1; i <= 20; i++) {
      Order order = new Order(i);
      order.addItem(DrinkType.LATTE, 2, false);
      order.addItem(DrinkType.MOCHA, 3, true);
      cafe.placeOrder(order);
    }
  }
}
