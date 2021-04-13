package victor.training.spring.events.events;

import lombok.Value;

@Value
public class OrderIsInStockEvent {
   long orderId;
}
