package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private static final Logger log = LoggerFactory.getLogger(PlayTransactions.class);
  private final JdbcTemplate jdbcTemplate; // 2001
  private final OtherClass other;
  private final DataSource dataSource;
  private final ApplicationEventPublisher applicationEventPublisher;

//  public void transactionsInJDBC_90sStyle() {
//    Connection conn = dataSource.getConnection(); // from a JDBC Connection pool default .size = 10
//    conn.setAutoCommit(false); // begin transaction
//    conn.createStatement().executeQuery("UPDATE")
//    conn.createStatement().executeQuery("UPDATE")
//    conn.commit();
//  }

  // stores the tx in a ThreadLocal on the current thread (spring-web)
  // stores the tx in reactor-context (spring-webflux): TODO ask the reactor trainer
  @Transactional // blocks 1 of the 10 JDBC connections of this app for the duration of this method
  public void play() throws IOException {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100, 'SQL' )");
    //rest/webClient.get() ... //if this API call takes 1 min,
    // if 10 calls hit play() method => 0 connections available in the pool for 1 min
    // => no other endpoint in this instance can reach DB for 1 min = DOS
    // maximum pool size reached  alarm => your app might slow down having to wait for a connection to be freedup
    //
    applicationEventPublisher.publishEvent(new EventToProcessAfterCommit("data"));
    try {
      other.extracted();
    } catch (Exception e) {
      log.warn("Ignoring: " + e, e);
//      throw new IOException(e);
//      jdbcTemplate.update("insert into MESSAGE(id, message) values (100412, 'error' )");
    }
    System.out.println("Exiting method");
  }

  record EventToProcessAfterCommit(String data) {}

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void method(EventToProcessAfterCommit event) {
    System.out.println("Rabbit/Kafak/Email send: " + event);
  }
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final JdbcTemplate jdbcTemplate;
  //  @Transactional(propagation = Propagation.NOT_SUPPORTED)// suspends the old, and enters the method with NO TX
//  @Transactional(propagation = Propagation.REQUIRES_NEW)
//  @Transactional
  public void extracted() {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100, 'second' )");
//    throw new RuntimeException(
//        "a runtime exception going out through a @Transactional proxy makes " +
//        "the entire tx irreversibly invalid");
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
