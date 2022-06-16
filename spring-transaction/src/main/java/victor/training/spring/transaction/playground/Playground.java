package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.engine.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Service
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
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'null' )");

        // faci insert invalid aici dar crapa 7 metode mai sus cand se face in cele din urma COMMIT. ==> Uaaaa!! :((((9
        //entityManager.flush();// folosesti temporar, doar pt debug ca afecteaza perf

        log.debug("Dupa ce ies din fct proxyul reia controlul si da commit la conn");
    }

//    @Transactional
    public void transactionTwo() {
        Message message = repo.findCuTaguri(100L);
        System.out.println("Am datele. cica");
        System.out.println(message.getTags());


        repo.save(new Message("O singura scriere"));
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
}