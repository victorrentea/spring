package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;



@Slf4j
@Service
public class InvoiceService {
  @EventListener // they run in the same thread as publisher
  // blockign the publisher until theyre done
  // in some arbitrary order
  // on exception, next listener(s) do not run!
//   @Order(20)
   @Async // WTF is this??
   public void sendInvoice(StockReservedEvent event) {
//    if (...) throw new UncheckedIOException()
      log.info("Generating invoice for order " +event.orderId());
      // if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
      log.info(">> PERSIST Invoice!!");
   }
}
