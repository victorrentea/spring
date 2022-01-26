package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Transactional
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {
}

@Facade
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final AnotherClass other;
    private final MyBatisMapper mybatis;

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        @Transactional
    protected void transactionOne() {
//        em.lock(message, LockModeType.PESSIMISTIC_WRITE);
        // SELECT FOR UPDATE * FROM MESSAGE WHERE ID = 1; COMMIT/ROLLBACK
        repo.save(new Message("jpa"));
        try {
            other.someOtherTransactedMethod();
        } catch (Exception e) {
            // despite handling the ex, the transaction was already made 'zombie' by the proxy
            // on the someOtherTransactedMethod()
            other.saveError(e);
        }
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
    }

    // commit at the end if everything goes well. On exception: rollback
    //@Transactional // "inherited" from @Facade
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi
    }
}


@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
//    @Transactional
    public void someOtherTransactedMethod() {
        throw new RuntimeException("e");
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional(propagation = Propagation.NOT_SUPPORTED) //also works
    // beause repo.save() has an internal @Transactional in the implem that sees NO active TX and creates a brand new one
    public void saveError(Exception e) {

        // no tx on current thread
        //jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )"); // auto-commit method

        repo.save(new Message("ERROR: " + e.getMessage()));
    }
}