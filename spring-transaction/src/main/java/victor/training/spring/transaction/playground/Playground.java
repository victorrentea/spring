package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
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
//    new RuntimeException().printStackTrace();// uite in log TransactionInterceptor
    jdbc.update("insert into MESSAGE(id, message) values (100, ?)", "SQL");
    log.info("1");
    repo.save(new Message("Tranzactia se mosteneste"));
    other.extracted();
  }
}
@Service
@RequiredArgsConstructor
@Slf4j
class OtherClass {
  private final MessageRepo repo;
  @Async
  public void extracted() { // orice metoda chemata dintr-o met @Transactional 'mosteneste' tranzactia
    log.info("2");
    repo.save(new Message("JPA"));
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
