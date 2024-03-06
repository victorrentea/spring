package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class Playground {
  private final JdbcTemplate jdbcTemplate;
  private final OtherClass otherClass;

  @Transactional
  public void play() {
    System.out.println("On what otherClass do I call my method ?" + otherClass.getClass().getCanonicalName());
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
    try {
      otherClass.anotherMethodICall();
    } catch (Exception e) {
      //
    }
  }
}

// what's wrong with transactions:
// they require and hold a connection to the database for the entire duration of the transaction
// they can cause DB connection starvation issues

@Service
@RequiredArgsConstructor
class OtherClass {
  private final JdbcTemplate jdbcTemplate;

  @Transactional (propagation = Propagation.REQUIRES_NEW)
    //(propagation = Propagation.NEVER)// throws: only use in @Test to block @Transactional inherited from AbstractPostgresTest
             // or in a method tha only reads data (SELECT) unless you do SELECT FOR UPDATE! - row-level lock
  public void anotherMethodICall() {
    System.out.println("Second");
    https://rcoh.me/posts/postgres-unique-constraints-deadlock/
//    jdbcTemplate.update("insert into MESSAGE(id, message) values (102,'SQL' )");
    jdbcTemplate.update("insert into MESSAGE(id, message) values (102,null )");
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
