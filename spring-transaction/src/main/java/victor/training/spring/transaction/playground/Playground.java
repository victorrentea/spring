package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final JdbcTemplate jdbc;
    private final OtherClass other;


    @Transactional
    public void transactionOne() {
        System.out.println("START OF METHOD");
        jdbc.update("insert into MESSAGE(id, message) values ( 100,? )", "first");
       other.method();
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

    @Transactional
    public void method() {
        jdbc.update("insert into MESSAGE(id, message) values (1,?)", "met2 1");
        jdbc.update("insert into MESSAGE(id, message) values (2,?)", "met2 2");
    }
}