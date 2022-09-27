package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class Playground2 {
    private static final Logger log = LoggerFactory.getLogger(Playground.class);
    private final JdbcTemplate jdbc;
    private final OtherClass2 other;

//        Connection conn;
//        conn.setAutoCommit(false); // = START TRANSACTION
//        conn.prepareStatement("").executeUpdate();
//        conn.commit();
//        conn.rollback();

    @Transactional(rollbackFor = Exception.class)
    public void transactionOne() throws FileNotFoundException {
        System.out.println("START OF METHOD");

        jdbc.update("insert into MESSAGE(id, message) values ( 100,? )", "first");
//        try {
            other.method();
//        } catch (Exception e) {
//            log.error("Oups: " + e);
//             NOTE: no rethrow; the ex dissapears.
//        }
        jdbc.update("insert into MESSAGE(id, message) values ( 101,? )", "why does this go to DB in a zombie TX?!");
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
class OtherClass2 {
    private final JdbcTemplate jdbc;

    @Transactional // kills the 'current' (caller) tx when an exception is thrown
    public void method() throws FileNotFoundException {
        jdbc.update("insert into MESSAGE(id, message) values (1,?)", "met2 1");
//        jdbc.update("insert into MESSAGE(id, message) values (null,?)", "met2 2");
        throw new FileNotFoundException();
    }

}