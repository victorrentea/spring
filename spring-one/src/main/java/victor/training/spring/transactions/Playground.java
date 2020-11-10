package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
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
        // ce e iai tranzactie, si are pH mic ?
        //  A C I D
        // Atomic
        // Consistent = Nu incalci FK (de ex)
        // Isolation = adica ceea ce tu faci in Tx ta nu vede altu pana n-ai comis-o
        // Durabil = se scrie pe disk la commit.

        // daca n-are tx, primeste o mica tranzactie doar pentru ea. (AUTO-COMMIT mode)
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )"); // plain old JDBC (ala de-l stie si profu tau)

        // ORM : sa te minta ca-s doar obiecte:
        // PK este generat automat dintr-o secventa din DB

        em.persist(new Message("jpa")); // Java Persistence API 30%

//        repo.save(new Message("jpa")); // Spring Data JPA 60%
    }
    @Transactional
    public void transactionTwo() {
//        jdbc.update("update MESSAGE set message = 'DOI' where ID = 100");
//
//        Message message = repo.findById(1L).get();
//        message.setMessage("DOI");

        // TODO Repo API
        // TODO @NonNullApi
    }
}
@Service
@RequiredArgsConstructor
class AnotherClass {
    private final MessageRepo repo;
    
    public void method() {

    }
}