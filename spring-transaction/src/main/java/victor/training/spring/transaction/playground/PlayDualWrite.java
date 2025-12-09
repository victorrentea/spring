package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayDualWrite {
  private final MyEntityRepo repo;

  @Transactional
  public void saveAndSend() {
    repo.save(new MyEntity("E12"));
    repo.save(new MyEntity("E22"));
    send(new MyMessage("M")); // ❌ => ex => rollback
  }
// SEND KAFKA/RABBIT/PUB-SUB
// INSERT + @NotNull @Size ❌
// INSERT
// COMMIT

  public record MyMessage(String content){}

  private void send(MyMessage message) {
    log.info("kafkaTemplate.send("+message+") pretend");
    log.info("restTemplate.post("+message+") pretend API CALL");
//    if (Math.random() < .5) throw new RuntimeException("BOOM"); // may fail
  }
}
