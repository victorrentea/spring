package victor.training.micro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class MessageListener {

    @Bean
    public Consumer<Message<String>> paymentRequestSubscriber() {
        return m -> {
            log.debug("Received " + m);
            throw new RuntimeException("INTENTIONAT");
        };
    }
}
