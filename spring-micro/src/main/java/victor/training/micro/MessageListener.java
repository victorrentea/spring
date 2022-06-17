package victor.training.micro;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Configuration
public class MessageListener {

    @Value
    public static class TransferMessage {
        String iban;
        int amount;
        String reason;
    }
    @Bean
    public Function<Message<TransferMessage>, String> paymentRequestSubscriber() {
        return message -> {
            log.debug("Processing " + message);
//            throw new RuntimeException("INTENTIONAT crap " + message.getPayload());
            String transactionId = UUID.randomUUID().toString();
            log.debug("reply with " + transactionId);
            return transactionId;
        };
    }


//    public class CustomErrorHandler implements ErrorHandler {
//        @Override
//        public void handleError(Throwable t) {
//            if (!(t.getCause() instanceof BusinessException)) {
//                throw new AmqpRejectAndDontRequeueException("Error Handler converted exception to fatal", t);
//            }
//        }
//    }
}
