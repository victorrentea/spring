package victor.training.spring.first.strategy;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(50)
public class InternationalOrderHandler implements OrderHandler {
  @Override
  public boolean canHandle(GroceryOrder order) {
    return "INTERNATIONAL".equalsIgnoreCase(order.getType());
  }

  @Override
  public void handle(GroceryOrder order) {
    System.out.println("InternationalOrderHandler handling " + order);
  }
}

