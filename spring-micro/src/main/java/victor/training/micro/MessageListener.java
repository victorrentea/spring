package victor.training.micro;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class MessageListener {

//    @Value
//    public static class TransferMessage {
//        String iban;
//        int amount;
//        String reason;
//    }
//    @Bean
//    public Consumer<Message<String>> paymentRequestSubscriber() {
//        return message -> {
//            log.debug("Processing " + message);
//            throw new RuntimeException("INTENTIONAT crap " +message.getPayload());
//        };
//    }
}
