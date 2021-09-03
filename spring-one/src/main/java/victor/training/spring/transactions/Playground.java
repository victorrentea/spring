package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;

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
        // because there was not TX on current threa dalread, the transaction proxy does:
        // open a transaction (a conn is acquired from the conn pool)
        // on that connection, a tx is started
        // the conn becomes bound to the current thread thread
        try {
            jdbc.update("insert into MESSAGE(id, message) values ( ?,? )", 100, "ALO");
            other.otherMethod();
            // commit - OK
        } catch (RuntimeException e) {
            // rollback  - OK
            // rethrow e
            throw e;
        } catch (Exception e) {
            // commit - OK - for historical reasons (stealing devs from JavaEE/EJB3 --> Spring in ~2005)
            // rethrow e
            throw e;
        }
    }
    @Transactional
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi
    }
}

@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final JdbcTemplate jdbc;
    @Transactional // Proxy
    public void otherMethod() {
        // "we are already in a transaction" - on the current thread
//        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        try {
            throw new FileNotFoundException("a.txt");
        } catch (Exception e) {
//             throw new Error(e); ~ Runtime
            throw new RuntimeException(e); // best practice: don't leak a Checked exception onto the head of your caller.
        }
    }
}