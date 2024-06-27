package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Playground {
  private final JdbcTemplate jdbc; // NU JPA/ORM/Hibernate. SQL curat.
  //  private final EntityManager entityManager; // JPA in stilu Java EE
  private final MessageRepo repo; // moderna cu JPA
  private final OtherClass other;

  @Transactional
  public void play() {
    try {
      jdbc.update("insert into MESSAGE(id, message) values (100, ?)", "SQL");
      repo.save(new Message("Tranzactia se mosteneste"));
      other.extracted();
    } catch (Exception e) {
      repo.save(new Message("Error in transaction: " + e.getMessage()));
      throw e;
    }
  }
}

@Service
@RequiredArgsConstructor
@Slf4j
class OtherClass {
  private final MessageRepo repo;
  //  @Async
//  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void extracted() { // orice metoda chemata dintr-o met @Transactional 'mosteneste' tranzactia
    repo.save(new Message("JPA"));
    throw new RuntimeException("Eroare");
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
