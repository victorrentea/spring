package victor.training.spring.transaction.playground;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayJpa {
  private final MessageRepo repo;
  private final EntityManager entityManager;

  @Transactional(isolation = Isolation.READ_UNCOMMITTED) // intr-o tx daca JPA ti-a dat un @Entity, cand o iei din nou dupa ID nu mai face SELECT, ti-o dat in cache
  public void writeBehind() {
    var id = repo.save(new Message("ONE")).getId();
    log.info("Dupa save");
    // #1 @Entity e in memoria Java
    log.info("Gasesc?: " + repo.findById(id)); // nu face SELECT IN DB pt ca il iei dupa ID = 1st-level JPA caching
    log.info("Gasesc?: " + repo.findByMessage("ONE")); // Face INSERT ca urmeaza un SELECT IN DB
    log.info("Gasesc?: " + repo.findByMessage("nullu nu face bine la om")/*.orElseThrow().getMessage()*/);
    // #2 @Entity e INSERT in DB (dar nu COMMITuit inca)
//    if (true) throw new RuntimeException("INTENTIONAL");
    log.info("--- End of method");
  }
  // #3 @Entity e INSERT in DB (si COMMITuit)

  @Transactional // dirty check la final => UPDATE
  public void autoSave() {
    Message message = repo.findById(1L).orElseThrow();
    message.getTags().add("TAG1"); // INSERT
    message.setMessage("Different"); // UPDATE in DB
    entityManager.detach(message); // quickfix la panica: JPA, uit-o

    repo.save(new Message("a"));
    repo.save(new Message("b"));
    // TODO send update in DB
  }

  public void lazyLoading() {
    Message entity = repo.findById(1L).orElseThrow();
    log.info("Message: " /*+ entity*/);
  }
}
