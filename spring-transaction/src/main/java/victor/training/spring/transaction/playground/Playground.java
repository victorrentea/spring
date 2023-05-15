package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    @Autowired
    private OtherClass otherClass;

    // REGULA: pui adnotarea doar
    // daca in fluxul tau faci 2+ interactiuni de modificare cu vreun repo
    // in orice metoda de sub tine.
    // MAGIE: tranzactia pornita aici se va "PROPAGA" in jos pe orice chemi
    @Transactional
    // proxy creaza tranzactie si o PUNE PE THREAD (ThreadLocal)
    public void transactionOne() {
        repo.nativeQueryInSpringDataJPA("ALO");
        try {
            otherClass.altaMetoda();
        } catch (Exception e) {
            otherClass.saveError(e);
//            sendKafka()
        }
        System.out.println("Acum salvez!");
        repo.save(new Message("Mai ruleaza linia asta ?"));
        //        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
    }



    @Transactional
    public void transactionTwo() {
    }
}
@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
    public void altaMetoda() {
        repo.save(new Message(null));
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW) // proxyule, fa o tranzacatie noua te rog peste aceasta metoda.
    // suspendand-o p'aia veche
    public void saveError(Exception e) {
        repo.save(new Message("VALEU EROARE: " + e.getMessage().substring(0, 100)));
    }
}