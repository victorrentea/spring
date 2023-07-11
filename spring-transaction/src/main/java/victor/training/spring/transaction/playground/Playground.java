package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  private final JdbcTemplate jdbc; // SQL direct
  private final OtherClass other;

  @Transactional
  public void transactionOne() {
    jdbc.update("insert into MESSAGE(id, message) values ( 100,'SuQiLi' )");
//        try {
//            other.altaMetoda();
//        } catch (Exception e) {
//            other.saveError(e);
//        }
//        repo.save(new Message("DUPA"));
    // 0 p6spy
    // 1 Cause a rollback by breaking NOT NULL, throw Runtime, throw CHECKED
    // 2 Tx propagates with your calls (in your thread😱)
    // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
    // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
    // 5 Performance: connection starvation issues : debate: avoid nested transactions
  }


  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @Transactional
  public void transactionTwo() {
    jdbc.update("UPDATE  MESSAGE set  message='changed' where id = 100");
    eventPublisher.publishEvent(new SendMailEvent());
//    throw new IllegalArgumentException("POC");
  }
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void sendEmail(SendMailEvent event) {
    System.out.println("S-a dus mailul!");
//    mailSender.send
  }
//  @Autowired
//MailSender mailSender;
}

class SendMailEvent {

}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;

  @Transactional // deschide tx daca n-ai, sau o ia pe cea curenta daca ai.
  public void altaMetoda() {
    repo.save(new Message("OK"));
    repo.save(new Message("OK2"));
    System.out.println("Ies din metoda!");
    if (true) {
      throw new IllegalArgumentException("Biz validation exception care distruge tranzactia curenta");
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW) // creeaza tranzactie separata pt aceasta metoda singura.
  public void saveError(Exception e) {
    repo.save(new Message("EROARE TATA: " + e));
  }
}