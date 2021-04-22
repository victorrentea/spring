package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Retention(RetentionPolicy.RUNTIME)
@Service
@Transactional
@interface Facade {

}
@Slf4j
@Facade
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final AnotherClass other;

//    @Transactional
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        repo.save(new Message("jpa"));
    }


//    @Transactional
    @SneakyThrows
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
            newTx.execute(status -> {
                storeError("ERROR w tx template");
                return null;
            });
        }

//        Connection connection = DataSourceUtils.getConnection();
//        connection.prepareStatement()
//        Playground myselfProxied = (Playground) AopContext.currentProxy();
        System.out.println("zombie? " + TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());

        repo.save(new Message("new new"));
        System.out.println(repo.findByMessage("a"));
//        repo.flush();
        System.out.println("END OF METHOD");

        other.goThroughThisMethodForAnyLongQuery().get();
    }

    @Autowired
    private TransactionTemplate newTx;

    @Autowired
    private Playground myselfProxied;

//    @Autowired
//    PlatformTransactionManager transactionManager;
//    UserTransaction userTransaction;

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void storeError(String message) {

        Message entity = new Message("ERROR: " + message);
        repo.save(entity);
        entity.setMessage("ERROR DIFF");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)//(readOnly = true)
    public void transactionOneBis() {
        Message message = repo.findById(100L).get();
        message.setMessage("Surprise");
        System.out.println("Entity got back");
        for (Tag tag : message.getTags()) {
            System.out.println("Tag: " + tag.getName());
        }

        Message messageBis = other.deepMeth();
        System.out.println(message == messageBis);
//        Connection connection;
//        c.ab
        repo.streamAll()
            .forEach(System.out::println);
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



    public Message deepMeth() {
        Message messageBis = repo.findByMessage("Surprise");
        return messageBis;
    }
    @Async("throttledTo20")
    @Transactional(readOnly = true)
    public CompletableFuture<List<String>> goThroughThisMethodForAnyLongQuery() {
        return CompletableFuture.completedFuture(Collections.emptyList());
    }
    @Transactional
    public void methodSharingTransaction() throws IOException {
        new RuntimeException().printStackTrace();
        repo.save(new Message("happy"));
        throw new RuntimeException("some reason");
    }


}