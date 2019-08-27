package victor.training.spring.messages;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceActivatorPattern {
    private final Source source;
    public void askInParallel() {
        log.info("Sending messages");
//        source.output().send(MessageBuilder.withPayload(...).build());
        log.info("Sent messages");
    }
}

@Slf4j
// TODO MessageEndpoint
class MessageHandler {

    // TODO ServiceActivator for queue Sink
    // TODO check parallelism config
//    public void handleOrder(message) {
//
//    }

}