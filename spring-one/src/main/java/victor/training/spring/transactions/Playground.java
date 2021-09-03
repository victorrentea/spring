package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.ThreadUtils;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final AnotherClass other;
    private final MyBatisMapper mybatis;

    @Transactional//(rollbackFor = Exception.class)
//    @TransactionAttribute // EJB 3
    public void transactionOne() {
        // because there was NO TX on current thread alread, the transaction proxy does:
        // open a transaction (a conn is acquired from the conn pool)
        // on that connection, a tx is started
        // the conn becomes bound to the current thread thread
        try {
            jdbc.update("insert into MESSAGE(id, message) values ( ?,? )", 100, "Biz Data");
            other.otherMethod();
            // commit - OK
        } catch (RuntimeException e) {
            // rollback  - OK
            // rethrow e
//            throw e;
            System.err.println("Ingoring: " + e); // shallow the exception.
            other.persistError(e.getMessage());
        } catch (Exception e) {
            // commit - OK - for historical reasons (stealing devs from JavaEE/EJB3 --> Spring in ~2005)
            // rethrow e
            throw e;
        }
        // at this point, the current Tx is "marked for rollback"
        jdbc.update("insert into MESSAGE(id, message) values ( ?,? )", 104, "More data");
//        SecurityContextHolder.getContext().getAuthentication().getpr
    }

//    @Transactional
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi
        log.debug("Start flow");
        Long aLong = jdbc.queryForObject("SELECT 1 from DUAL", Long.class);
        httpCall100ms();

        other.atomicMet();

    }

    private void httpCall100ms() {
        ThreadUtils.sleep(200);

    }

}

@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
@Transactional
class AnotherClass {
    private final JdbcTemplate jdbc;
//    @Transactional // Proxy rollbacks on any runtime exception
    public void otherMethod() {
        // "we are already in a transaction" - on the current thread
        jdbc.update("insert into MESSAGE(id, message) values ( 110,'in other in the same tx' )");
        try {
            throw new FileNotFoundException("a.txt");
        } catch (Exception e) {
//             throw new Error(e); ~ Runtime
            throw new RuntimeException(e); // best practice: don't leak a Checked exception onto the head of your caller.
        }
    }
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void persistError(String message) {
        jdbc.update("insert into MESSAGE(id, message) values ( ?,? )", 101, "Error : " + message);
    }

    @Transactional(readOnly = true)
    public void atomicMet() {
        jdbc.update("insert into MESSAGE(id, message) values ( ?,? )", 105, "withdrawal");
        met();
    }

    private void met() {
        jdbc.update("insert into MESSAGE(id, message) values ( ?,? )", 106, "payment");
    }
}