package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
  private final JdbcTemplate jdbcTemplate; // if you don't have JPA in your app
  // myBatis = primitive ORM
  private final EntityManager entityManager; // JPA
  private final MessageRepo repo; // Spring Data JPA << ⭐️
  // jooq  = DSL
  // In the chat what tech to talk to SQL db you use:
  // jdbcTemplate, EntityManager, mybatis, jooq

  private final OtherClass other;

  @Transactional // what does this do?
  // starts a tx at the start and ATTEMPT to commit the tx at the end if no exception is thrown
  // we NEED transactions only if we CHANGE data in DB (DML: update,insert,delete)
  public void transactionOne() {
    repo.save(new Message("JPA")); // an INSERT is gonna happen at the end of the Tx (FLUSH)
    System.out.println("WTF: write-behind= JPA waits for the tx to finish OK before auto-flushing any pending changes");
    try {
      other.secondMethod();
    } catch (Exception e) {
      log.error("OMG " + e);
    }
  } // after the end of tx, INSERT in DB + COMMIT

  public void transactionTwo() {}
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;

  @Transactional(propagation = Propagation.REQUIRES_NEW) // why to use it?
  public void secondMethod() {
    repo.save(new Message("JPA2")); // rolledback
    throw new IllegalArgumentException("Oups");
  }

}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
