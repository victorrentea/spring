package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import static java.lang.Math.random;

@Slf4j
@Service
public class InvoiceService {
   @Order(2)
   @EventListener
   public void process(OrderPlacedEvent event) {
      log.info("Generating invoice for order " + event.orderId());
       if (random() < .3) throw new RuntimeException("Invoice Generation Failed for @Marko");
      log.info(">> PERSIST Invoice!!");
   }
}
