package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager jpaCurat;
    private final OtherClass otherClass;
    // 1 singura instanta pt toata app
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Transactional // aici proxy deschide tx noua
    public void transactionOne() {
        threadLocal.set("EU ACUM"); // doar th curent vede aceasta valoare
        repo.suchili("SQL NATIV");
        try {
            otherClass.bizLogic();
        } catch (Exception e) {
            log.error("O crapat " + e);
            totEuDarProxiat.saveInNewTransaction(e);
        }
        alta();
        // asta nu trimite in DB inca INSERTUL pana cand nu
//        repo.saveAndFlush(new Message("SQL NATIV")); // daca vrei sa dizablezi write behind, si sa trimit INSERT in DB pe loc, dar poti pierde performanta
        // se face FLUSH
        log.info("Write-behind (JPA) = ce aveai de scris in DB se flush() abia la finalul tx inainte de COMMIT");
//        rabbit.send(new Message("GATA!!!"));
    } // proxy face flush > commit

    @Autowired
    @Lazy
    private Playground totEuDarProxiat;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveInNewTransaction(Exception e) {
        repo.saveAndFlush(new Message("ERROR: " + e.getMessage()));
    }

    //    @Transactional
    private void alta() {
        System.out.println(threadLocal.get());
        repo.save(new Message("SQL NATIVx")); // chiar daca sunt in alta metoda locala, tranzactia vine dupe mine
        // cum puiiiii mei !?!?!?!?!?!
    }

    // cum putem bloca propagarea tranzactiei ?
    // comportamentul la exceptii
    // propagation= ?....
    // Best practices : cum ne purtam cu @Transactional
    @Transactional
    public void transactionTwo() {
        System.out.println(repo.findByMessageContainingIgnoreCase("lO"));
        System.out.println(repo.findByMessageLike("LO"));
    }
}
@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
    @Transactional // proxyul cand vede exceptie iesind din metoda asta,
    // marcheaza tranzactia curenta (venita de la caller) ca "rollback-only"
    // din acest moment incolo, nu ai cum sa invii tranzactia asta.
    public void bizLogic() {
        repo.save(new Message("Treaba Mea"));
//        repo.save(new Message(null)); // NOT NULL violation
//        repo.save(new Message("")); // javax.validation annotaions
         if (true) throw new IllegalArgumentException();
    }
}