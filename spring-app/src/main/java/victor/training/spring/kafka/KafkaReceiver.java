package victor.training.spring.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Profile("kafka")
@Slf4j
@Component
public class KafkaReceiver {
  @KafkaListener(topics = "demo", groupId = "spring-app")
  public void receive(NewUser newUser) {
    log.info("Received from Kafka: {}", newUser);
  }
}
