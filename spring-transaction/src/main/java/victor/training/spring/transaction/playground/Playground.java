package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import victor.training.spring.jooq.tables.Message;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class Playground {
  private final JdbcTemplate jdbcTemplate;
  private final OtherClass otherClass;
    private final DSLContext jooq;


  @Transactional
  public void play() {
//      jooq.insertInto(Message.MESSAGE)
//          .set(Message.MESSAGE.ID, 100L)
//          .set(Message.MESSAGE.MESSAGE_, "SQL")
//          .execute();
    System.out.println("On what otherClass do I call my method ?" + otherClass.getClass().getCanonicalName());
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
    try {
      otherClass.anotherMethodICall();
    } catch (Exception e) {
      //
    }
  }

  @Transactional
  public void stuff() {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'BAD' )");
//    restApi.call(); // for the duration of the call (drama:1sec), the connections is held
    // DO NOT ever do NETWORK calls in @Transactional methods: connection_acquisition_tiem
    // idea: let's increase the connection pool size: 10 -> 50 : danger for DB
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
  public void anotherMethodICall() {
    https://rcoh.me/posts/postgres-unique-constraints-deadlock/
    // when a transaction isnerts a value guarded by an unique constraint, it locks that UQ so if
    // no other transaction can insert the same value until the first Tx commits.
    jdbcTemplate.update("insert into MESSAGE(id, message) values (102,'SQL2' )");
//    jdbcTemplate.update("insert into MESSAGE(id, message) values (102,null )");
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions


// nesting transactions is dangerous: deadlocks and can cause
// connection starvation issues