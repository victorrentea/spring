package spring.training.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

interface Queues {
    String Q1_OUT = "q1out";
    @Output(Q1_OUT)
    MessageChannel q1out();

    String Q1_IN = "q1in";
    @Input(Q1_IN)
    SubscribableChannel q1in();

    String Q2_OUT = "q2out";
    @Output(Q2_OUT)
    MessageChannel q2out();

    String Q2_IN = "q2in";
    @Input(Q2_IN)
    SubscribableChannel q2in();
}