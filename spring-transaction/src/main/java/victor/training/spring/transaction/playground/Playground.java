package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class Playground {
  private final JdbcTemplate jdbc;
  private final OtherClass other;

  // check exceptions are a MISTAKE of the Java Language (no other language has them)
  // "leaking abstraction" and prone to "swallow"
  // NEVER throw checked exceptions from your methods. only runtime
  @Transactional// (rollbackFor = Exception.class)
  public void transactionOne()  {
//    Connection connection = ...;
//    connection.setAutoCommit(false);
    jdbc.update("insert into MESSAGE(id, message) values (100,?)", "SQL");
    other.method();
//    if (true) { // allowed credit limit exceed for customer
//      throw new RuntimeException("Too indebted");
//    }
//    connection.commit();
//    connection.rollback();;
  }
  @Transactional // Spring
//  @TransactionAttribute (EJB)
  public void transactionTwo() {}
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  private final JdbcTemplate jdbc;

//  @Async / stops the tx from wrapping over this method
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void method() {
    jdbc.update("insert into MESSAGE(id, message) values (101,'2ndTx' )");
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
