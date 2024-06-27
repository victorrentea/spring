package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Playground {
  private final JdbcTemplate jdbc; // NU JPA/ORM/Hibernate. SQL curat.
//  private final EntityManager entityManager; // JPA in stilu Java EE
  private final MessageRepo repo; // moderna cu JPA
  private final OtherClass other;

  @Transactional
  public void play() {
    new RuntimeException().printStackTrace();// uite in log TransactionInterceptor
    jdbc.update("insert into MESSAGE(id, message) values (100, ?)", "SQL");
    extracted();
  }

  private void extracted() { // orice metoda chemata dintr-o met @Transactional 'mosteneste' tranzactia
    repo.save(new Message("JPA"));
  }
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
