package victor.training.micro;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

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
    public Consumer<Message<TransferMessage>> paymentRequestSubscriber() {
        return message -> {
            try {
                doProcess(message);
            } catch (Exception e) {
                log.error("Valeu",e); // asta va ACK catre Rabbvit ca ai mancat mesajul si el in sterge.
            }
        };
    }

    private void doProcess(Message<TransferMessage> message) {
        log.debug("Processing " + message);
        throw new RuntimeException("INTENTIONAT crap " + message.getPayload());
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
