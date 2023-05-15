package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;

    // REGULA: pui adnotarea doar
    // daca in fluxul tau faci 2+ interactiuni de modificare cu vreun repo
    // in orice metoda de sub tine.
    // MAGIE: tranzactia pornita aici se va "PROPAGA" in jos pe orice chemi
    @Transactional
    // proxy creaza tranzactie si o PUNE PE THREAD (ThreadLocal)
    public void transactionOne() {
        repo.nativeQueryInSpringDataJPA("ALO");
        altaMetoda();
        // 0 p6spy
        // 1 Cause a rollback by breaking NOT NULL, throw Runtime, throw CHECKED
        // 2 Tx propagates with your calls (in your thread😱)
        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
    }

    private void altaMetoda() {
        repo.save(new Message("A"));
    }

    @Transactional
    public void transactionTwo() {
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
}