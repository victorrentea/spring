package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class Jpa {
  private final MessageRepo repo;
  private Long id;
//  private final EntityManager entityManager; JPA direct

  @Transactional
  public void one() {
    Message message = repo.save(new Message("JPA"));
    id = message.getId();
    log.info("End of method. inserted: " + id);
    // in log INSERT apare dupa iesirea din functie
    // Write-Behind = JPA amana INSERT/UPDATE in DB pana inainte de COMMIT. Atunci le face 'flush'
    // PROS: poate evita sa faca INSERT daca ROLLBACK; poate BATCHEUI impreuna mai multe inserturi
    // CONS: misleading. poate crapa UQ DUPA CE IESI din metoda.
    // poti evita featureul facand repo.saveAndFlush
  }

  public void two() {
    Message e = repo.findById(id).orElseThrow();
    e.setMessage("Different");
    repo.save(e);
    // TODO lazy loading
  }
}
