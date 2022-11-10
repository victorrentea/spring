package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        // TODO kafka/rabbit.send intr-un @TransactionalEventListener(phase=AFTER_COMMIT)
        log.info("Ies din metoda");
    }

    private void neAdnotata() { // ruleaza oricum in Tx pornita la :28
        repo.save(new Message("unu"));
    }

//    @Transactional
    public void transactionTwo() {
        Message message = repo.findById(1L).orElseThrow();
        message.setMessage("Altul!");// < aceasta modificare se duce in DB automat la finalul tranzactiei
        // orice entitate iti da JPA din repo, este monitorizata pentru modificar in cadrul unei @Transactional
        // NU AI NEVOIE DE SAVE ca sa se persiste changeul.
        // 'dirty check' la final de tx.
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