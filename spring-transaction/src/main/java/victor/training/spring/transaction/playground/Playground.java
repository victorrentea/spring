package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbc;
    private final OtherClass other;

    @Transactional
    public void transactionOne() throws IOException {
        jdbc.update("insert into MESSAGE(id, message) values ( ?,'ALO' )", 100L);
        repo.save(new Message("null"));


        FileReader fileReader = new FileReader("c:\\workspace\\notonmac");
        System.out.println(fileReader.read());


        log.info("End of method  +" );
        // 0 p6spy
        // 1 Cause a rollback by breaking NOT NULL, throw Runtime, throw CHECKED
        // 2 Tx propagates with your calls (in your thread😱)
        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
    }
    @Transactional
    public void transactionTwo() {
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
}