package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.aspectj.runtime.CFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

@Service

@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final OtherClass other;

    @Transactional
    public void transactionOne() {
        try {
            bizLogic();
        } catch (Exception e) {
            playgroundWTF.altaMetoda(e);
            throw new RuntimeException(e);
        }
        System.out.println("TOTU BUN ACUM!");
        // cum e posibil?
        // 0 p6spy
        // 1 Cause a rollback by breaking NOT NULL, throw Runtime, throw CHECKED
        // 2 Tx propagates with your calls (in your thread😱)
        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
    }

    @Autowired
    private Playground playgroundWTF;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void altaMetoda(Exception e) {
        repo.save(new Message("Valeu a crapat cu " + e));
    }

    private void bizLogic() {
        repo.save(new Message("jpa")); // NU ramane in DB => a fost ATOMICA cu save #2;
        if (Math.random() < .5) {
            throw new IllegalArgumentException("Crapau!");
        }
    }

    private void metoda() {
        repo.save(new Message(null));
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