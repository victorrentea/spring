package victor.training.spring.first.strategy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderProcessor processor;

  public OrderController(OrderProcessor processor) {
    this.processor = processor;
  }

  @PostMapping("/process")
  public ResponseEntity<String> process(@RequestBody ProcessOrderRequest req) {
    GroceryOrder order = new GroceryOrder(req.getId(), req.getType().name());
    processor.process(order);
    return ResponseEntity.ok("Processed");
  }
}

