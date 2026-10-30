package victor.training.spring.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("kafka") // start the app with --spring.profiles.active=kafka
@RequiredArgsConstructor
@RestController
public class KafkaSender {
  private final KafkaTemplate<String, NewUser> kafkaTemplate;

  // curl -u user:user "localhost:8080/api/kafka/send?name=Jane%20Doe&username=jane"
  @GetMapping("api/kafka/send")
  public void send(@RequestParam String name, @RequestParam String username) {
    kafkaTemplate.send("demo", new NewUser(name, username));
  }
}
