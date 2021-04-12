package victor.training.spring.events.stock;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import victor.training.spring.events.events.OrderInStockEvent;
import victor.training.spring.events.events.OrderPlacedEvent;

import java.util.function.Function;

//interface StockChannels {
//    String stockRequestIN = "rsin";
//    @Input(stockRequestIN)
//    SubscribableChannel rsin();
//
//   String generateInvoiceOUT = "giout";
//   @Output(generateInvoiceOUT)
//   MessageChannel giout();
//}
@MessageEndpoint
class StockRequestMessageHandler {
   private static final Logger log = LoggerFactory.getLogger(StockRequestMessageHandler.class);
//   @ServiceActivator(
//       inputChannel = StockChannels.stockRequestIN,
//       outputChannel = StockChannels.generateInvoiceOUT)
//   public Message<String> handle(String payload) {
//      log.debug("Handling stock request message : " + payload);
//      return MessageBuilder.withPayload("Generate invoice " + payload).build();
//   }

}

@Slf4j
//@EnableBinding(StockChannels.class)
@SpringBootApplication
public class StockManagementServiceApp {

   @Bean
   public Function<String, Message<String>> checkStock() {
      return payload -> {
         log.debug("Handling stock request message : " + payload);
         return MessageBuilder.withPayload("Generate invoice " + payload).build();
      };
   }

   public static void main(String[] args) {
       SpringApplication.run(StockManagementServiceApp.class, args);
   }
}
