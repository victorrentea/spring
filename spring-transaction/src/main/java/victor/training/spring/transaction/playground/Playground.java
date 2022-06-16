package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@Service
//@Transactional // NICIODATA daca ai volum mare de cereri sau dureaza mult req pe care le faci
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbc;
    private final OtherClass other;

    @Transactional
    public void transactionOne() {
        repo.save(new Message("a")); //asta nu scrie inca in DB ci il lasa in
        // Persistence COntext (aka Session) care functioneaza aici ca un WRITE CACHE
        // nu trimite direct INSERTUL ci asteapta sf tranzactiei cu succes.
        // de ce ? pt performanta. Va putea ulterior sa batchuiasca mai multe inserturi impreuna
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'original' )");

        // faci insert invalid aici dar crapa 7 metode mai sus cand se face in cele din urma COMMIT. ==> Uaaaa!! :((((9
        //entityManager.flush();// folosesti temporar, doar pt debug ca afecteaza perf

        log.debug("Dupa ce ies din fct proxyul reia controlul si da commit la conn");
    }

    @Transactional
    public void transactionTwo() {
        Message message = repo.findCuTaguri(100L);
        message.setMessage("Altu");

        try {
            other.operatieRiscanta();
        } catch (Exception e) {
            repo.save(new Message("Eroare mare : " + e.getMessage()));
        }
        System.out.println(repo.findAll());
    }

    //@Transactional
    public void celMaiFrecventAntiPatternCuTransactional() {
//    Message praf = repo.findById(13L).get();
//    new RestTemplate().get   (url) // e ok ei raspund in 2000ms
//
//        repo.save(new Message("daca scriu in baza, tre tranzactie mi-a spus amma"));
////    repo.save(new Message("real nevoie"));
    }
}

@Service
@RequiredArgsConstructor
//@Transactional
class OtherClass {
    private final MessageRepo repo;

    @Transactional(propagation = REQUIRES_NEW)
    public void operatieRiscanta() {
        throw new RuntimeException("Method not implemented");
    }
}