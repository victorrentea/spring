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
        Message first = new Message("First");
        repo.save(first);
        System.out.println("Should be assigned : " + first.getId());
        repo.save(new Message(null));// when using JPA , any persist/merge
        // will only write into a WRITE CACHE = PersistenceContext that will be sent to DB over JDBC just at the end on the Tx.
        jdbc.update("insert into MESSAGE(id, message) values ( 100, ?)", "ALO");
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