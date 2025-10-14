package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
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
  private final MessageRepo repo; // = Spring Data JPA, 2011
  private final OtherClass other;

  @Transactional
  public void playx2() {
    play();
    play();
  }
  
  @Transactional
  public void play() {
    repo.save(new Message("JPA").addTag("eticheta"));
    other.extracted();
//    System.out.println("Rabbit send "+ "mesaj"); // facut aici, se intampla inainte de COMMIT
    eventPublisher.publishEvent(new MesajDeTrimis("mesaj"));
    System.out.println("--------------------------");
  }
  record MesajDeTrimis(String mesaj){}
  private final ApplicationEventPublisher eventPublisher;
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  // ruleaza dupa commitul tx din care a fost publicat eventul
  public void trimite(MesajDeTrimis mesaj) {
    System.out.println("Rabbit send " + mesaj.mesaj);
  }
  // JPA WRITE-BEHIND: insert/update/delete sunt trimise in DB exact inainte de commit
  // -- pt performanta: BATCHING, poate nu-i nevoie ca crapa pe drum
  // ! in ciuda FLUSH, transactional in continuare tine tot ATOMIC
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  @SneakyThrows
  @Async // alt thread => alta tranzactie
  @Transactional//(propagation = Propagation.REQUIRES_NEW)//(rollbackFor = Exception.class) //~@TransactionAttribute din EJB
  public void extracted()  {
    Thread.sleep(1000);
    repo.save(new Message("JPA2"));
//    if (true) throw new RuntimeException("Runtime causes rollback");
    //if (true) throw new IOException("Checked causes commit! ca au copiat <CENZURAT> din EJBullshit");
  }
  @Transactional//(propagation = Propagation.REQUIRES_NEW)
  public void txNoua(Exception e) {
    new RuntimeException("Doar sa vad stack").printStackTrace();
    repo.saveAndFlush(new Message(e.getMessage()));
  }
}








// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions


