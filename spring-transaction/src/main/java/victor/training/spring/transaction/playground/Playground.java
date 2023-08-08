package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Playground {
  private final JdbcTemplate jdbc;
  private final OtherClass other;

  @Transactional
  public void transactionOne() throws IOException {
    jdbc.update("insert into MESSAGE(id, message) values (100,?)", "SQL");
    jdbc.update("insert into MESSAGE(id, message) values (101,'jooq' )");
    if (true) { // allowed credit limit exceed for customer
      throw new IOException("Too indebted");
    }
  }

  @Transactional
  public void transactionTwo() {}
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
