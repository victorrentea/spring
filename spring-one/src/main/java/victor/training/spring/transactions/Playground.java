package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo messageRepo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final AnotherClass other;

    @Transactional
    public void transactionOne() {
        jdbc.update("insert into TEACHER(ID, NAME) VALUES ( 99, 'Profu de Mate' )");
        try {
            other.method();
        } catch (Exception e) {
            // shaworma - posibil viitor career path daca faci asta des.
            jdbc.update("insert into MESSAGE(id, message) values ( 100,'Error: "+e.getMessage()+"' )");
        }
    }
    @Transactional
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi
        Message message = messageRepo.findById(100L).get();
        message.setMessage("alt mesaj");
    }
}
@Service
@RequiredArgsConstructor
class AnotherClass {
    private final MessageRepo repo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void method() {
        throw new IllegalArgumentException("Intentioanta");
    }
}