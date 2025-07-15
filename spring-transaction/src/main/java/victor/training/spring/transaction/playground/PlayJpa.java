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

  @Transactional//(readOnly = true) // DE CE AI NEVOIE DE TX daca nu modifici baza?
  public void autoSave() {
    Message entity = repo.findById(1L).orElseThrow();
    entity.setMessage("Different");
//    repo.save(new Message("Nou"));
    // chiar fara repo.save changeul tau ajunge in DB
    // La flush, JPA compara starea obiectului dat tie din findById
    // cu o COPIE pe care si-a facut-o starii persistente initiale
  }

//  @Transactional(readOnly = true) // ca sa mearga lazy loading
  public void lazyLoading() {
    Message entity = repo.findById(1L).orElseThrow();
    log.info("Luat din DB");
    log.info("Message: " + entity);
  }
}
