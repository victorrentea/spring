package victor.training.spring.transaction;

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
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        mybatis.search(100);
        repo.save(new Message("jpa"));
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
    private final MessageRepo repo;
}