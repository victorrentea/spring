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
    Message message = repo.findById(1L).orElseThrow();
    message.setMessage("Different");
    // TODO send update in DB
  }

  public void lazyLoading() {
    Message entity = repo.findById(1L).orElseThrow();
    log.info("Message: " /*+ entity*/);
  }
}
// TODO
//  - write behind = insert/update/delete sent to DB (=flush) after method end, before tx COMMIT
//  - flush (!= commit) also triggered: before any SELECT, repo.saveAndFlush, repo.flush()
//  - auto-save any changes to an @Entity returned by JPA within a tx
//  - lazy loading requires surrounding tx (or http request with open-session-in-view/SpringBoot)
//  - JPA 1st level cache = findById(id) returns previous entity from memory (without SELECT)