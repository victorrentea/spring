package victor.training.spring.events.events;

import org.springframework.context.ApplicationEvent;

public class OrderInStockEvent {
   private final long orderId;

   public OrderInStockEvent(long orderId) {
      this.orderId = orderId;
   }

   public long getOrderId() {
      return orderId;
   }
}
