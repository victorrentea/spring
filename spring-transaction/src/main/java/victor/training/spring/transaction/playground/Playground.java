package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

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

@Value
class EventuMeu {
    String marire;
}

@Slf4j
@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void bizLogic() {
        repo.save(new Message("Business Data1"));
        repo.save(new Message("Business Data2"));

//                DefaultTransactionDefinition td = new DefaultTransactionDefinition();
////                td.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//                TransactionStatus transaction = transactionManager.getTransaction(td);
//                transaction.setRollbackOnly();

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
//        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        txTemplate.executeWithoutResult(status -> {
            status.setRollbackOnly();
            // linia 1
            // linia 2
        });

//        eventPublisher.publishEvent(new EventuMeu("nu")); // sync public
//        if (true) {
//            System.out.println("#situ");
//            throw new IllegalArgumentException("BUG!");
//        }
    }

    @Autowired
    private PlatformTransactionManager transactionManager;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onEvent(EventuMeu eventuMeu) {
        log.info("dupa commit trimit mailuri! : " + eventuMeu.getMarire());
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