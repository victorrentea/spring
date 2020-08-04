package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    public void transactionOne() throws Exception {
        Message message = new Message("jpa");
        repo.save(message);
        repo.save(new Message("jpa2"));
        System.out.println(message.getId());
        Message jpa = repo.findByMessage("jpa");
        System.out.println(jpa);
        try {
            other.method();
        } catch (Exception e) {
            // TODO handle
            // O inghit p'asta. Shaworma
        }
    }
    @Transactional
    public void transactionTwo() {
        // ceva dubios care arunca exceptie
        try {
            other.bigBadWorkflow();
        } catch (Exception e) {
            other.saveError(e.getMessage());
        }

        // TODO Repo API
        // TODO @NonNullApi
    }
}
@Service
@RequiredArgsConstructor
class AnotherClass {
    private final MessageRepo repo;
    @Transactional(propagation = Propagation.REQUIRES_NEW) //suspenda ex tranzactiei cu care ai venit, si executa fara
    public void method() {
        repo.save(new Message("auto-commit"));

        throw new RuntimeException(); // gresit sa arunci d'alea de le faci throws, catch () {shaworma}
    }



    @Transactional
    public void bigBadWorkflow() {
        throw new IllegalArgumentException("ceva rau!");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveError(String message) {
        repo.save(new Message(message));
    }
}