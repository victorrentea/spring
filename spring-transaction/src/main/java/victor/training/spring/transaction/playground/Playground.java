package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class Playground {
    private static final Logger log = LoggerFactory.getLogger(Playground.class);
    private final JdbcTemplate jdbc;
    private final OtherClass other;

    @Transactional
    public void transactionOne() throws FileNotFoundException {
        System.out.println("START OF METHOD");

        jdbc.update("insert into MESSAGE(id, message) values ( 100,? )", "first");
        other.notAnnotatedMethodWithinTheSameThread();
        try {
            other.method();
        } catch (Exception e) {
            other.persistError(e);
        }
        // 0 p6spy
        // 1 Cause a rollback by breaking NOT NULL, throw Runtime, throw CHECKED
        // 2 Tx propagates with your calls (in your thread😱)
        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
        System.out.println("END OF METHOD");
    }




    @Transactional
    public void transactionTwo() {
    }
}
@Service
@RequiredArgsConstructor
class OtherClass {
    private final JdbcTemplate jdbc;


    public int notAnnotatedMethodWithinTheSameThread() {
        return jdbc.update("insert into MESSAGE(id, message) values ( 99,? )", "first");
    }
    @Transactional
    public void method() {
        jdbc.update("insert into MESSAGE(id, message) values (1,?)", "met2 1");
        jdbc.update("insert into MESSAGE(id, message) values (2,?)", "met2 1");
        throw new RuntimeException("Somthing went bad"); // bad luck
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int persistError(Exception e) {
        return jdbc.update("insert into MESSAGE(id, message) values ( 999,? )", "Error: " + e);
    }


}