package victor.training.spring.first.strategy;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(100)
@Component
public class DefaultOrderHandler implements OrderHandler {
  @Override
  public boolean canHandle(GroceryOrder order) {
    return true;
  }

  @Override
  public void handle(GroceryOrder order) {
    System.out.println("OnlineOrderHandler handling " + order);
  }
}

