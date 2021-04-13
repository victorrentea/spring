package victor.training.spring.events.stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;


@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class StockManagementServiceApp {

   public static void main(String[] args) {
       SpringApplication.run(StockManagementServiceApp.class, args);
   }
   private int stock = 3; // silly implem :D

   @Bean
   public Function<String, Message<String>> checkStock() {
      return orderId -> {
         log.info("Checking stock for products in order " + orderId);
         if (stock == 0) {
            throw new IllegalStateException("Out of stock");
         }
         stock--;
         log.info(">> PERSIST new STOCK!!");
//      return new OrderIsInStockEvent(event.getOrderId());
         return MessageBuilder.withPayload("Order in STOCK " + orderId).build();
      };
   }
}
