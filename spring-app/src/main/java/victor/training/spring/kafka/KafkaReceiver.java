package victor.training.spring.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.entity.UserRole;
import victor.training.spring.web.repo.UserRepo;

import java.util.List;

@Profile("kafka")
@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaReceiver {
  private final UserRepo userRepo;

  @KafkaListener(topics = "demo", groupId = "spring-app")
  public void receive(NewUser newUser) {
    if (userRepo.existsByUsername(newUser.username())) {
      log.info("User {} already exists, ignoring: {}", newUser.username(), newUser);
      return;
    }
    User user = userRepo.save(new User(newUser.name(), newUser.username(), UserRole.USER, List.of(), null));
    log.info("Inserted user id={} from Kafka: {}", user.getId(), newUser);
  }
}
