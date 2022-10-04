package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    public void transactionOne() { // ascultam mesaje de pe cozi
        try {
            other.bizLogic();
        } catch (Exception e) {
            other.saveError(e);
        }
    }


    //    // 2 Tx propagates with your calls (in your thread😱)
//        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
//        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
//        // 5 Performance: connection starvation issues : debate: avoid nested transactions
//    @Transactional
//    public void transactionTwo() {
//    }
}
@Slf4j
@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
    @Transactional
    public void bizLogic() {
        repo.save(new Message("Business Data1"));
        repo.save(new Message("Business Data2"));
        if (true) {
            throw new IllegalArgumentException("BUG!");
        }
    }

    public void saveError(Exception e) {
        repo.save(new Message("VALEU: EROARE: " + e.getMessage()));
    }

    @Async
    public void oAltaMetoda() {
        log.info("pe ce thread sunt Doamne?");
        repo.save(new Message(null));
    }
}