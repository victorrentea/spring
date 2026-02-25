package victor.training.spring.first.strategy;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(10)
@Component
public class InStoreOrderHandler implements OrderHandler {
  @Override
  public boolean canHandle(GroceryOrder order) {
    return "INSTORE".equalsIgnoreCase(order.getType());
  }

  @Override
  public void handle(GroceryOrder order) {
    System.out.println("InStoreOrderHandler handling " + order);
  }
}

