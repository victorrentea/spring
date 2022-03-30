package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final AnotherClass other;
    private final MyBatisMapper mybatis;

    // a JDBC connection is BOUND to a thread.
    @Transactional // A
    public void transactionOne() {
        // logic
        try {
            jdbc.update("insert into MESSAGE(id, message) values ( 101,'OK')");
            other.stuff();
        } catch (Exception e) {
           other. saveError(e);
        }
            jdbc.update("insert into MESSAGE(id, message) values ( 1025,'NEVER')");
        System.out.println("End of method");
    }


    public void transactionTwo() {
        // now works in autocommit mode
        jdbc.update("insert into MESSAGE(id, message) values ( 601,'null')");
        // row is commited in DB.💗
    }
}


@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
    private final JdbcTemplate jdbc;

//    @Transactional(propagation = Propagation.SUPPORTS)
    public void stuff() {
        throw new RuntimeException("BUM");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveError(Exception e) {
        jdbc.update("insert into MESSAGE(id, message) values ( 105,'ERROR: ' || ?)", e.getMessage());
    }
}