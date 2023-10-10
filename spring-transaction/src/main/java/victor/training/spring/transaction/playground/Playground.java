package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  private final JdbcTemplate jdbcTemplate;
  private final OtherClass other;


  @Transactional
  public void transactionOne() {
    repo.sqlNativ();
    other.f();
    repo.sqlNativ();
  }

  public void transactionTwo() {}
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void f() {
    repo.save(new Message("null")); // JPA insert
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
