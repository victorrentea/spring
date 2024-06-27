package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class Playground {
  private final JdbcTemplate jdbc; // NU JPA/ORM/Hibernate. SQL curat.
  //  private final EntityManager entityManager; // JPA in stilu Java EE
  private final MessageRepo repo; // moderna cu JPA
  private final OtherClass other;

  @Transactional
      //(rollbackFor = Exception.class)// Solutia #1
  public void play() {
    try {
      jdbc.update("insert into MESSAGE(id, message) values (100, ?)", "SQL");
      repo.save(new Message("Tranzactia se mosteneste"));
      other.extracted();
    } catch (Exception e) {
//      other.saveError(e);
      eventPublisher.publishEvent(new PlayError(e));
      throw e;
    }
  }
  private final ApplicationEventPublisher eventPublisher;
}
record PlayError(Exception e) {}

@Service
@RequiredArgsConstructor
@Slf4j
class OtherClass {
  private final MessageRepo repo;
  // dupa ce s-a rollbackuit tx din care s-a facut publish, executa metoda asta.
  @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveError(PlayError event) {
    log.info("Rulez #sieu");
    repo.save(new Message("Error in transaction: " + event.e().getMessage()));
    log.info("End");
  }
  //  @Async
//  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void extracted() { // orice metoda chemata dintr-o met @Transactional 'mosteneste' tranzactia
    try {
      repo.save(new Message("JPA"));
      throw new FileNotFoundException("Eroare"); // checked exception
    } catch (FileNotFoundException e) {
      // solutia #2 NU MAI ARUNCA NICIODATA IN APLICATIE JAVA BACKED checked exceptions!!
      throw new RuntimeException(e);
    }
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
