package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final JdbcTemplate jdbc;
    private final EntityManager em;
    private final MessageRepo repo; // Spring Data JPA

    private final AnotherClass other;

    @Transactional
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        em.persist(new Message("JPA"));
        repo.save(new Message("Spring Data JPA"));

        System.out.println(repo.findByMessage("JPA")); // causes a flush() pentru ca ceea ce avea de pus in baza ar putea influenta rez queryului.
        log.debug("Ies din metoda");
    }


    @Transactional
    public void transactionTwo() {

        Message message = repo.findById(100L).get();

        message.setMessage("alt mesaj");

        // TODO Repo API
        // TODO @NonNullApi
    }
}


@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
}