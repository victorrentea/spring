package victor.training.spring.first.strategy;

public interface OrderHandler {
  boolean canHandle(GroceryOrder order);
  void handle(GroceryOrder order);
}

