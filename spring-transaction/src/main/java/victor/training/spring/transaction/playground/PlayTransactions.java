package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.sql.DataSource;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final MessageRepo repo;
  private final OtherClass other;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public void play() {
    other.play2();
    repo.save(new Message("JPA"));
    applicationEventPublisher.publishEvent(new AfterMyCommit());
  }
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void afterCommit(AfterMyCommit event) {
    System.out.println("After Commit");
  }

  record AfterMyCommit() {}


  @Transactional
  public void forFrancesco() throws InterruptedException {
    Thread.sleep(1000);
//    dsl.stuff();
    //then look at localhost:8080/actuator/prometheus for the metrics
    // hikaricp.connections.usage.seconds.sum  and .count
  }
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  @Transactional // enlisting in the existing transaction on the current thread
  public void play2() {
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
