package victor.training.spring.events.invoicing;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Queues {

    String Q2_IN = "q2in";
    @Input(Q2_IN)
    SubscribableChannel q2in();
}