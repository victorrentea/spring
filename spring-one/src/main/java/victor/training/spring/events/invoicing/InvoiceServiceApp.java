package victor.training.spring.events.invoicing;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import victor.training.spring.events.events.OrderInStockEvent;

import java.io.IOException;

interface InvoiceChannels {
   String generateInvoiceIN = "giin";
   @Input(generateInvoiceIN)
   SubscribableChannel giin();
}


@MessageEndpoint
class GenerateInvoiceMessageHandler {
   private static final Logger log = LoggerFactory.getLogger(GenerateInvoiceMessageHandler.class);
   @ServiceActivator(inputChannel = InvoiceChannels.generateInvoiceIN)
   public void handle(Message payload) {
      log.debug("Handling generate invoice : " + payload);
   }
//   @StreamListener
//   public void handlerMethod(@Payload String foo,
//                             @Header(AmqpHeaders.CHANNEL) Channel channel,
//                             @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
//      channel.basicAck(deliveryTag, false);
//   }

}


/// ----------- visez ca peste X ani de aici in jos mut in alta aplicatie (microserviciu)
@Slf4j
@EnableBinding(InvoiceChannels.class)
@SpringBootApplication
public class InvoiceServiceApp {public static void main(String[] args) {
    SpringApplication.run(InvoiceServiceApp.class, args);
}

   @EventListener
   public void sendInvoice(OrderInStockEvent event) {
      long orderId = event.getOrderId();
      log.info("Generating invoice for order " + orderId);
      // TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
      log.info(">> PERSIST Invoice!!");
   }
}
