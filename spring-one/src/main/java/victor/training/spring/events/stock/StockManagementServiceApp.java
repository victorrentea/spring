package victor.training.spring.events.stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;


interface StockChannels {
   @Input("oce_in")
   SubscribableChannel orderCreatedEvents();
   @Output("gi_out")
   MessageChannel generateInvoice();
}
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@EnableBinding(StockChannels.class)
public class StockManagementServiceApp {

   public static void main(String[] args) {
       SpringApplication.run(StockManagementServiceApp.class, args);
   }
   private int stock = 3; // silly implem :D

   @Autowired
   StockChannels channels;

   @ServiceActivator(inputChannel = "oce_in", outputChannel = "gi_out")
   public Message<String> process(String orderId) {
      log.info("Checking stock for products in order " + orderId);
      if (stock == 0) {
         throw new IllegalStateException("Out of stock");
      }
      stock--;
      log.info(">> PERSIST new STOCK!!");
//      return new OrderIsInStockEvent(event.getOrderId());
      return MessageBuilder.withPayload("Order in STOCK " + orderId).build();
   }
}
