package victor.training.spring.first.strategy;

import org.springframework.stereotype.Component;

@Component
public class OnlineOrderHandler implements OrderHandler {
  @Override
  public boolean canHandle(GroceryOrder order) {
    return "ONLINE".equalsIgnoreCase(order.getType());
  }

  @Override
  public void handle(GroceryOrder order) {
    System.out.println("OnlineOrderHandler handling " + order);
  }
}

