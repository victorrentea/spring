package victor.training.spring.events.invoicing;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.SubscribableChannel;
import victor.training.spring.events.events.OrderInStockEvent;

interface InvoiceChannels {
   String generateInvoiceIN = "giin";

   @Input(generateInvoiceIN)
   SubscribableChannel giin();
}


@MessageEndpoint
class GenerateInvoiceMessageHandler {
   private static final Logger log = LoggerFactory.getLogger(GenerateInvoiceMessageHandler.class);

   @ServiceActivator(inputChannel = InvoiceChannels.generateInvoiceIN)
   public void handle(String payload) {
      log.debug("Handling generate invoice : " + payload);
   }

}


/// ----------- visez ca peste X ani de aici in jos mut in alta aplicatie (microserviciu)
@Slf4j
@EnableBinding(InvoiceChannels.class)
@SpringBootApplication
public class InvoiceServiceApp {
   public static void main(String[] args) {
      SpringApplication.run(InvoiceServiceApp.class, args);
   }
}
