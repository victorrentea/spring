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
//    et2();
    id = repo.save(new Message("ONE")).getId();
    log.info("End of method ---"); // WRITE-BEHIND = insert apare dupa ce iesi din functie, exact inainte de COMMIT
    // 1) ca sa nu faca insert daca urmeaza rollback
    // 2) ca sa poata BATCHEUI inserturile impreuna.
    //   Batch insert ca sa reduci durata jobului de la 8h la 20min (24x)
//    throw new RuntimeException("Boom");
  }
      private void et2() {
        beci();
//        repo.flush(); // ex aici
        System.out.println("Inainte");
        System.out.println("#sieu: "+repo.findByMessage("ONE"));
        mansarda();
      }
          private void beci() {
            repo.save(new Message("ONE")); // forteaza INSERTUL in DB
            //+ crapa UQ -=>exceptie la botu' calului
            //- disableaza batching, ca trimite repede pe loc INSERTUL
          }
          private void mansarda() {
            id = repo.saveAndFlush(new Message("ONE2")).getId();
          }
  //@GetMapping
  @Transactional
  public void two() {
    Message m = repo.findById(id).orElseThrow();
    m.setMessage("CHANGED");
    repo.save(m);
    repo.save(new Message("AUDIT"));
  }
}
