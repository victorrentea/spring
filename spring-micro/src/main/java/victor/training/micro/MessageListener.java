package victor.training.micro;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Configuration
public class MessageListener {

//  @KafkaListener("some-topic")
//  public void method(Message<String> message) {
//    log.info("Processing " + message);
//    throw new RuntimeException("INTENTIONAT crap " + message.getPayload());
//  }

  @Bean
  public Function<Message<String>, Message<String>> paymentRequestSubscriber() {
    return message -> {
      log.info("Processing " + message);
      String replypayload = "Reply: " + message.getPayload().toUpperCase();
      return MessageBuilder.withPayload(replypayload).build();
    };
  }

  @Value
  public static class TransferMessage {
    String iban;
    int amount;
    String reason;
  }
}
