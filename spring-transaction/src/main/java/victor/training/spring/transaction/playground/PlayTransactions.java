package victor.training.spring.transaction.playground;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.sql.DataSource;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final JdbcTemplate jdbcTemplate; // since 2001
  private final OtherClass otherClass;
  @Transactional 
  public void play() {
    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (100,?)","SQL");
    otherClass.second();
    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (105,?)","SQLxx");
  }
//  @Transactional //~= @TransactionAttribute(from EJB)
//  public void play() throws IOException {
//    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (100,'SQL')");
//    if(true) throw new IOException("Known 💩 in Java. 😱😱 COMMIT UNTIL HERE! WTFeat?");
//    // when Spring was born, everyone was using EJB (massive and intrusive) ~2005
//    // what todo? ⇒ NEVER EVER EVER use 'throws' in methods of Spring beans (mistake anyway)
//    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (101,'SQL2')");
//    if(true) throw new IllegalArgumentException("rollsback the transaction");
//  }
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final JdbcTemplate jdbcTemplate;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional // this proxy enlists in the existing transcation on the thread / Reactor Context
  public void second() {
    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (101,'SQL2')");
// restTemplate/webClient.get ⚠️to another [slow] [external] system might take long => COnnection Pool Starvation => delay in reaching by db by any flow
    // BAD PRACTICE to do API calls from @Transactional methods
    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (102,'SQL3')");
    applicationEventPublisher.publishEvent(new MyEvent());
  }
  record MyEvent() {}
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // transaction hook
  // spring runs this method after the commit of the transaction from withing the event was published (second() method)
  public void afterCommit(MyEvent event) {
    // SEND an email after the commit of this transaction
    System.out.println("Email sent for commit " + event);
  }
}






// TODO
//  0 p6spy shows connection id, commit/rollback, actual query params (not ?) - everywhere < prod
//  1 rollback on runtime exception, commit on checked exception 🤬
//  2 Tx propagates with your calls (on thread)
//  3 @Tx on local method called within the same class - has no effect
//  4 Tx propagation control REQUIRES_NEW or NOT_SUPPORTED
//  5 Performance: JDBC connection starvation
