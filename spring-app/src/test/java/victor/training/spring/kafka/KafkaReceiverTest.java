package victor.training.spring.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.repo.UserRepo;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

// a Kafka broker started inside this JVM, just for this test
@EmbeddedKafka(topics = "demo")
@SpringBootTest(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}", // instead of localhost:9093
        "spring.kafka.consumer.auto-offset-reset=earliest"}) // don't miss a message sent before the listener got its partition
@ActiveProfiles("kafka")
class KafkaReceiverTest {
  @Autowired
  KafkaTemplate<String, NewUser> kafkaTemplate;
  @Autowired
  UserRepo userRepo;

  @Test
  void insertsTheReceivedUserInDatabase() {
    kafkaTemplate.send("demo", new NewUser("Jane Doe", "jane"));

    await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
      User user = userRepo.findByUsernameForLogin("jane").orElseThrow();
      assertThat(user.getName()).isEqualTo("Jane Doe");
    });
  }
}
