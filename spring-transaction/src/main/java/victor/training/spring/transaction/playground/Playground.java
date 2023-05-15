package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
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
        otherClass.altaMetoda();
        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
    }

    @Autowired
    private OtherClass otherClass;

    @Transactional
    public void transactionTwo() {
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
    @Async
    public void altaMetoda() {
        repo.save(new Message(null));
    }
}