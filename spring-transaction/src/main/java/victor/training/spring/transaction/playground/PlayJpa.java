package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayJpa {
  private final MessageRepo repo;
  private final OtherClass otherClass;

  @Transactional
  public void writeBehind() {
    repo.save(new Message("ONE")); // primeste id=1
    repo.save(new Message("TWO")); // primeste id=2
    System.out.println(repo.count());
    log.info("--- End of method");
  }

  @Transactional
  public void autoSave() { // ruleaza dupa writeBehind
    Message entity = repo.findById(1L).orElseThrow();
    entity.setMessage("TWO");// ajunge in DB si fara repo.save
    // TODO scrie aici ceva in DB care sa ramana chiar daca tx curenta da ROLLBACK
    otherClass.oricumComite();
  }



  @Transactional(readOnly = true) // necesar pentru lazy load
  public void lazyLoading() {
    Message entity = repo.findById(1L).orElseThrow();
    log.info("Luat entity din DB --- ");
    log.info("Message: " + entity.getTags()); // un SELECT ascuns sa-ti scoata si copii
  }
}
