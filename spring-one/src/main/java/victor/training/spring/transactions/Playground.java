package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.transaction.TransactionManager;

@Value
class CleanUpFiles{
    String tempFileName;
}

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo messageRepo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final AnotherClass other;

    private TransactionTemplate transactionTemplate;

    @Autowired
    public void initTxTemplate(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    @Autowired
    ApplicationEventPublisher publisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void cleanupFilesAfterTxEnd(CleanUpFiles cleanUpFiles) {
        System.out.println("Sterg fisiere temporare : " + cleanUpFiles);
    }

//    @Transactional
    public void transactionOne() {
//        transactionTemplate.execute(status -> {
            jdbc.update("insert into TEACHER(ID, NAME) VALUES ( 99, 'Profu de Mate' )"); // merge si fara TX
            try {
                publisher.publishEvent(new CleanUpFiles("temp.tmp"));
                other.method();
//                messageRepo.save(m); // merge si fara TX
//            em.persist(); // crapa fara TX
            } catch (Exception e) {
                // shaworma - posibil viitor career path daca faci asta des.
    //            jdbc.update("insert into MESSAGE(id, message) values ( 100,'Error: "+e.getMessage()+"' )");
            }
//            return null;
//        });
    }
    @Transactional
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi
        Message message = messageRepo.findById(100L).get();
        message.setMessage("alt mesaj");
    }
}
@Service
@RequiredArgsConstructor
class AnotherClass {
    private final MessageRepo repo;
    private final JdbcTemplate jdbc;

//    @Async("myServiceExecutor")
//    @Transactional/*(propagation = Propagation.REQUIRES_NEW)*/
    public void method() {
        jdbc.update("insert into TEACHER(ID, NAME) VALUES ( 101, 'Profu de Mate' )");
        throw new IllegalArgumentException("Intentioanta");
    }
}