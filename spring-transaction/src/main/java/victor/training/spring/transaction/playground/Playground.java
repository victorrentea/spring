package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbc;
    private final OtherClass other;

    //@TransactionAttribute (EJB)
    @SneakyThrows
    @Transactional
    public void transactionOne() {
        repo.insertNativ();
        other.oAltaMetoda();
        Thread.sleep(100);
    }

    // 2 Tx propagates with your calls (in your thread😱)
        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
    @Transactional
    public void transactionTwo() {
    }
}

@Slf4j
@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;

    @Async
    public void oAltaMetoda() {
        log.info("pe ce thread sunt Doamne?");
        repo.save(new Message(null));
    }
}