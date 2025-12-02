package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayJpa {
  private final MessageRepo repo;

  public void writeBehind() {
    var id = repo.save(new Message("ONE")).getId();
    log.info("Gasesc?: " + repo.findById(id));
    log.info("--- End of method");
  }

  public void autoSave() {
    Message message = repo.findById(1L).orElseThrow();
    message.setMessage("Different");
    // TODO send update in DB
  }

  public void lazyLoading() {
    Message entity = repo.findById(1L).orElseThrow();
    log.info("Message: " /*+ entity*/);
  }
}
