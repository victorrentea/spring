package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Connection;

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
        repo.save(new Message("unu"));
        try {
            other.metoda();
        } catch (Exception e) {
            // TODO aflu maine
        }
        repo.save(new Message("trei, dupa ce Tx a explodat"));
        System.out.println("Query:"+ repo.findByMessage("a"));
        // TODO kafka/rabbit.send intr-un @TransactionalEventListener(phase=AFTER_COMMIT)
        log.info("Ies din metoda");
    }

    @Transactional
    public void transactionTwo() {
    }
}
@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
//
    @Transactional(rollbackFor = Exception.class) // proxy acesta ACUM omoara TX curenta
    public void metoda() throws IOException {
        repo.save(new Message("doi"));
//        throw new IllegalArgumentException("VALEU!"); // vreun check, datele aduse din alta parte nu te lasa sa continui, BUG, NPE
        throw new IOException("oups"); // proxy-ul considera ca o ex CHECKED nu ar trebui sa cauzeze ROLLBACK
        // ce dobitoc a zis asta?! EJB-u'! mama lui.
            // pe vremea aia era in voga debate-ul BusinessException(checked) vs TechnicalException
            // de atunci lumea a realizat ca ex checked sunt o GRESEALA a lb Java! (nici un alt lb de progr din lume nu are ex checked)
            // 1995: faceau Java. peste tot in jur auzeai doar malloc/free, char*
            // astazi in proj noi, dau reject la PR daca vad throws ceva Checked ex prin cod de app logic
        // momentul de istorie......
        // Springul a venit dupa Iarna EJB2.x (<- cel mai agresiv standard de coding in BE din istorie) GUNOI de standard
        // springu a fost facut de un grup de haiduci ce s-au opus opresiunii standardarului JavaEE
        // "less invasive"
        // problema: in 2004 toti developerii de backend erau pe JavaEE. -> Spring:cum ii furam?
        // SCOP: sa le facem usoara tranzitia code-baseurilor JavaEE->Spring
            // @Transactional a copiat behaviorul @TransactionAttribute din java EE


        // morala:
        // 1 niciodata nu arunca ex checked din met cu app logic
        // 2 daca ai codebase vechi -> @Transactionl(rollbackfor=
    }
}