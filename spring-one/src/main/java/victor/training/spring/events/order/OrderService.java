package victor.training.spring.events.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.CheckStockForOrder;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

   private final ApplicationEventPublisher eventPublisher;

   public void placeOrder() {
      log.debug(">> PERSIST new Order");
      long orderId = 13L;
//      invoiceService.sendInvoice(orderId);

//      eventPublisher.publishEvent(new OrderCreatedEvent(orderId));// event
      eventPublisher.publishEvent(new CheckStockForOrder(orderId));
   }
}
