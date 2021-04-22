package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final AnotherClass other;

    @Transactional
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        repo.save(new Message("jpa"));
    }


    @Transactional
    public void transactionTwo() {
        Message message = repo.findById(100L).get();
        message.setMessage("Different");
        log.debug(message.getId() + " is the id");

//        repo.save(new Message(null));
//        System.out.println(repo.findByMessage("new new"));

//        repo.save(message);
//        repo.save(new Message("jpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpa"));

        try {
            other.methodSharingTransaction();
        } catch (Exception e) {
//            myselfProxied.storeError(e.getMessage());
            TransactionTemplate tx = new TransactionTemplate(transactionManager);
            tx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            tx.execute(status -> {
                storeError("ERROR w tx template");
                return null;
            });
        }
//        Playground myselfProxied = (Playground) AopContext.currentProxy();
        System.out.println("zombie? " + TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());

        repo.save(new Message("new new"));
        System.out.println(repo.findByMessage("a"));
        System.out.println("END OF METHOD");
    }



    @Autowired
    private Playground myselfProxied;

    @Autowired
    PlatformTransactionManager transactionManager;
//    UserTransaction userTransaction;

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void storeError(String message) {

        Message entity = new Message("ERROR: " + message);
        repo.save(entity);
        entity.setMessage("ERROR DIFF");
    }

}

class PlaygroundProxyPlay extends Playground {
    public PlaygroundProxyPlay(MessageRepo repo, EntityManager em, JdbcTemplate jdbc, AnotherClass other) {
        super(repo, em, jdbc, other);
    }

    @Override
    public void transactionTwo() {
        // start tx. put on thread.
        super.transactionTwo();
        // commit()
    }
}


@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;

    @Transactional
    public void methodSharingTransaction() throws IOException {
        new RuntimeException().printStackTrace();
        repo.save(new Message("happy"));
        throw new RuntimeException("some reason");
    }


}