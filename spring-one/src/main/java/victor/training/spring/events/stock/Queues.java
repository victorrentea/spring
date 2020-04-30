package victor.training.spring.events.stock;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Queues {

    String Q1_IN = "q1in";
    @Input(Q1_IN)
    SubscribableChannel q1in();

    String Q2_OUT = "q2out";
    @Output(Q2_OUT)
    MessageChannel q2out();
}