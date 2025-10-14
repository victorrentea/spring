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

  @Transactional
  public void autoSave() {
    Message entity = repo.findDeMana("N").orElseThrow();
    // in spate JPA isi tine o copie a datelor initiale
    entity.setMessage("Different"); // TODO send update in DB
  }// daca s-a modificat entity in metoda => update automat

  public void maiCrestin() {
    Message entity = repo.findByMessageContains("N").orElseThrow();
    entity.setMessage("Different");
    repo.save(entity);// TODO send update in DB
  }

  //@Transactional(readOnly = true) // ca nu modific
  public void lazyLoading() {
    Message entity = repo.findById(1L).orElseThrow();
    log.debug("Message: {}", entity); //bun!
  }
}
