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

  @Transactional
  public void writeBehind() {
    repo.save(new Message("ONE"));
    log.info("--- End of method");
  }

  public void autoSave() {
    Message entity = repo.findById(1L).orElseThrow();
    entity.setMessage("Different");
  }

  @Transactional
  public void lazyLoading() {
    Message entity = repo.findById(1L).orElseThrow();
    log.info("Message: " + entity);
  }
}
