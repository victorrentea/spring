package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class Jpa {
  private final MessageRepo repo; // Spring Data
//  private final EntityManager entityManager;// modu vechi de JPA
  private Long id;

  @Transactional
  public void one() {
    et2();
    log.info("End of method ---"); // WRITE-BEHIND = insert apare dupa ce iesi din functie, exact inainte de COMMIT
    // 1) ca sa nu faca insert daca urmeaza rollback
    // 2) ca sa poata BATCHEUI inserturile impreuna.
    //   Batch insert ca sa reduci durata jobului de la 8h la 20min (24x)
//    throw new RuntimeException("Boom");
  }
      private void et2() {
        beci();
        repo.flush(); // ex aici
        mansarda();
      }
          private void beci() {
            repo.save(new Message("ONE")); // forteaza INSERTUL in DB
            repo.save(new Message("ONE")); // forteaza INSERTUL in DB
            //+ crapa UQ -=>exceptie la botu' calului
            //- disableaza batching, ca trimite repede pe loc INSERTUL
          }
          private void mansarda() {
            id = repo.saveAndFlush(new Message("ONE2")).getId();
          }

  public void two() {
//    Message e = repo.findById(id).orElseThrow();
//    e.setMessage("Different"); // TODO auto-flush changes

    // TODO lazy-loading
  }
}
