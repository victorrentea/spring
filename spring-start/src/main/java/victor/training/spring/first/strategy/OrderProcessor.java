package victor.training.spring.first.strategy;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderProcessor {
  private final List<OrderHandler> handlers;

  public OrderProcessor(List<OrderHandler> handlers) {
    this.handlers = handlers;
  }

  public void process(GroceryOrder order) {
    Optional<OrderHandler> handler = handlers.stream()
        .filter(h -> h.canHandle(order))
        .findFirst();

    if (handler.isPresent()) {
      handler.get().handle(order);
    } else {
      System.out.println("No handler found for " + order);
    }
  }
}

