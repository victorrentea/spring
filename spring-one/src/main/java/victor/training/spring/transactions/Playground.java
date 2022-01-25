package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
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

    @Transactional
    public void transactionOne() {
        repo.save(new Message("jpa"));
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
//        throw new IOException("Something"); // allows to commit Tx unless @Transactional(rollbackFor=Exception.class)
        throw new RuntimeException("e");
    }
    // commit at the end if everything goes well. On exception: rollback

    @Transactional
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi
    }
}


@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
}