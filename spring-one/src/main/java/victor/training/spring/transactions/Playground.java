package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;

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
        repo.save(new Message("null"));
//        em.persist(new Message(null));

        try {
            other.altaMetodaTranzactata();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(repo.findByMessage("JPA")); // causes a flush() pentru ca ceea ce avea de pus in baza ar putea influenta rez queryului.
        log.debug("Ies din metoda");
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void transactionTwo() {

        Message message = repo.findById(100L).get();
        message.setMessage("alt mesaj");
    }
    @Transactional
    public void transactionThree(String mesajDePeCoada) throws IOException {
        try {
            Boolean eroare = null;
            repo.save(new Message("Am primit: " + mesajDePeCoada));
            if (eroare) {
                throw new IOException("NASPA FRATE");
            }
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            other.persistErorrInDifferentTx(e);
            throw e;
        }
        em.persist(new Message("In veci in DB"));
    }

}

@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistErorrInDifferentTx(Exception e) {
        repo.save(new Message("EROARE: " + e.getMessage()));
    }

    @Transactional
    public void altaMetodaTranzactata() {
        repo.save(new Message("Inca una mai flacai!"));
        throw new RuntimeException("Intentionata");
    }
}