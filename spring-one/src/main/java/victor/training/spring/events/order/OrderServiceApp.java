package victor.training.spring.events.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import victor.training.spring.events.events.CheckStockForOrder;

import java.util.Random;


interface OrderServiceChannels {
   @Output("oce_out")
   MessageChannel orderCreatedEvents();
}

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@EnableBinding(OrderServiceChannels.class)
public class OrderServiceApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(OrderServiceApp.class, args);
   }
   @Autowired
   OrderServiceChannels channels;

   public void placeOrder() {
      log.debug(">> PERSIST new Order");
      long orderId = new Random().nextInt(1000);
      channels.orderCreatedEvents().send(MessageBuilder.withPayload(orderId + "").build());
   }

   @Override
   public void run(String... args) throws Exception {
      placeOrder();
   }
}
