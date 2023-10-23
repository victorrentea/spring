package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.swing.event.MenuEvent;

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
  // what does this do?
  // starts a tx at the start and ATTEMPT to commit the tx at the end if no exception is thrown
  // we NEED transactions only if we CHANGE data in DB (DML: update,insert,delete)

  @Transactional
  public void transactionOne() {
    repo.save(new Message("JPA")); // will not wait for the end of the tx for the flush, send the INSERT right now in the CURRENT TX
    System.out.println("WTF: write-behind= JPA waits for the tx to finish OK before auto-flushing any pending changes");
    repo.save(new Message("JPA"));
//    activemq.send()
    eventPublisher.publishEvent(new MyEventAfterCommitToSendOnKafka("Message created id: /..."));
  } // after the end of tx, INSERT in DB + COMMIT
  public void transactionTwo() {}

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  // if the .publishEvent is NOT happening in a transaction -> event silently dissapears
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // Option 1
  public void afterCommit(MyEventAfterCommitToSendOnKafka event) {
    System.out.println("kafka.send()"); // what if kafka fails... retry... then call support by loggign some particular TOKEN to your log and scraping the logs rgularly

    // Option 2 or use the TRANSACTIONAL OUTBOX PATTERN
//    instead of a 'volatile' MyEventAfterCommitToSendOnKafka, INSERT a row into a KAFKA_MESSAGES_TO_SEND
    // that you then poll with a @Scheduled, removing lines when kafka send =ack

//    Option3: Change-Data-Capture with https://debezium.io

    // Option4: Transacted ActiveMQ/Solace JMS send() that can be rolledback
  }
}
record MyEventAfterCommitToSendOnKafka(String message) {}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  @Transactional
  public void secondMethod() {
    repo.save(new Message("JPA2")); // rolledback
    throw new IllegalArgumentException("Oups");
  }

}
