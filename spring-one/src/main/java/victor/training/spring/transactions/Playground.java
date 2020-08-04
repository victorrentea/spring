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
        // TODO Repo API
        // TODO @NonNullApi
    }
}
@Service
@RequiredArgsConstructor
class AnotherClass {
    private final MessageRepo repo;
    @Transactional
    public void method() {
        throw new RuntimeException(); // gresit sa arunci d'alea de le faci throws, catch () {shaworma}

    }

}