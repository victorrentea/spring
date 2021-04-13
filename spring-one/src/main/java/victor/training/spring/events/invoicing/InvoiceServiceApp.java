package victor.training.spring.events.invoicing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderIsInStockEvent;

interface StockChannels {
   @Input("gi_in")
   SubscribableChannel generateInvoice();
}
@Slf4j
@SpringBootApplication
@EnableBinding(StockChannels.class)
public class InvoiceServiceApp {
   public static void main(String[] args) {
       SpringApplication.run(InvoiceServiceApp.class, args);
   }
   @ServiceActivator(inputChannel = "gi_in")
   public void sendInvoice(String command) {
      log.info("Generating invoice for order " + command);
      // TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
      log.info(">> PERSIST Invoice!!");
   }
}
