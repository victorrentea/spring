package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo; //Data= Spring Data (interfete fara implem care extind JpaRepository)
    private final JdbcTemplate jdbcTemplate; // SQL murdar

    private final OtherClass other;
    // 0 p6spy✅
    // 1 Cause a rollback by breaking NOT NULL✅
    // spring data JPA: @Query, metode cu nume destepte, native, @Modifying

    //  , throw Runtime, throw CHECKED
    // 2 Tx propagates with your calls (in your thread😱)
    // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
    // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
    // 5 Performance: connection starvation issues : debate: avoid nested transactions

    @Transactional
    public void transactionOne() {
        neAdnotata();
        try {
            other.metoda();
        } catch (Exception e) {
            // TODO aflu maine
            other.saveError(e);
        }
        repo.save(new Message("trei, dupa ce Tx a explodat"));
        System.out.println("Query:" + repo.findByMessage("a"));

        eventPublisher.publishEvent(new KafkaMessageToSend("treaba"));
        // springul vede ca listenerul acestui mesaj este @TransactionalEventListener => adauga "callbackul" asta
        // la o lista de 'after commit' pentru tranzactia curenta. currentTransaction.afterCommitEvents.add(eventul tau);
        log.info("Ies din metoda");
    }
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value
    private static class KafkaMessageToSend {
       String ce;
    }

    @TransactionalEventListener(phase=AFTER_COMMIT)
    public void afterCommit(KafkaMessageToSend event) {
        log.info("ACUM TRIMIT PE KAFKA " + event.ce);
         new RuntimeException("e").printStackTrace();
        log.info("kafka.send");
    }

    private void neAdnotata() { // ruleaza oricum in Tx pornita la :28
        repo.save(new Message("unu"));
    }

    @Transactional
    //    @Transactional(readOnly = true) // JPA intelege sa nu faca INSERT/UPDATE/DELETE (ci doar pt niste magie de JPA)
    public void transactionTwo() {

        Message message = repo.findById(1L).orElseThrow();
        message.setMessage("Altul!");
        //        repo.save(message); // aici .save functioneaza nu ca INSERT ci ca UPDATE
        // programmatic update . no magic
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;

    //    @Transactional
    // ! singura data cand chem metoda() este din metoda din clasa de sus, care imi porneste tranzactie
    // => oricum am deja pe thread tx curenta.
    // Ce diferenta face adnotarea @Transactional daca DEJA vii cu tranzactie oricum deschisa ?
    // => face sa apara proxy care daca vede exceptii, distruge Tx.
    // de fapt la rece, metoda asta NU are nevoie de @Transactional intrucat NU are nevoie sa ATOMICIZEZE 2 operatii (ACID)
    public void metoda() {
        repo.save(new Message("doi"));
        boolean nuPotProcesaMesaju = true;
        if (nuPotProcesaMesaju) throw new IllegalArgumentException("VALEU!");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveError(Exception e) {
        repo.save(new Message("EROARE: " + e.getMessage()));
    }
}