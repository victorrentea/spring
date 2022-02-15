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

    @Transactional
    public void transactionOne() {

        // level 1 plain JDBC
//        Connection connection;
//        connection.prepareStatement("")

        // level 2 JDBCTemplate for direct SQL but helped by Spring
        jdbc.update("insert into MESSAGE(id, message) values ( 100, ?)", "ALO");

        // level 3 builds on Level1 ; Entity Manager = Session (hibernate)
        // em.createNativeQuery("insert into MESSAGE(id, message) values ( 100, ?)") // bad practice when using JPA
//        em.persist(new Message("plain JPA"));

        // level 4 (on top of 3) = Spring Data
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