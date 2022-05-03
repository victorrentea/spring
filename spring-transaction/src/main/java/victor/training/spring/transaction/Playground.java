package victor.training.spring.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        alta();
        System.out.println("AOLEU");
    }

    private void alta() {
        other.asaptea();
    }
    @Transactional
    public void transactionTwo() {

        try {
            other.bizLogic("mesaj de pe coada");
        } catch (Exception e) {
            saveError(e);
        }
    }
//    @TransactionAttribute()
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveError(Exception e) {
        repo.save(new Message("CRAPAU: " + e.getMessage()));
    }
}

@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;

    @Transactional
    public void asaptea() {
//        restTemplate.getForObject("REST CALL"); // 10s // WSDL evita apeluri HTTP din metode tranzactionale
        repo.save(new Message("null"));
//        repo.save(new Message(null));
    }

    @Transactional
    public void bizLogic(String mesaj_de_pe_coada) {
        repo.save(new Message("Chestii1 "));
        repo.save(new Message("Chestii2 "));
        // inserturi
        throw new RuntimeException("BUG");
    }


}