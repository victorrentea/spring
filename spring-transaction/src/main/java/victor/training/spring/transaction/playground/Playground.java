package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;

    @Transactional
    public void transactionOne() {
        repo.save(new Message("jpa1"));
//        justAnotherMethod();
        CompletableFuture.runAsync(() -> repo.save(new Message(null)));

        // 1 Cause a rollback by breaking NOT NULL, throw Runtime, throw CHECKED
        // 2 Tx propagates with your calls (in your thread😱)
        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
    }

    private void justAnotherMethod() {
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