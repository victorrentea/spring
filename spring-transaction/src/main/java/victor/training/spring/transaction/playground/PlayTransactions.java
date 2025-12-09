package victor.training.spring.transaction.playground;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayTransactions {
    private final DataSource dataSource; // since 1998
    private final JdbcTemplate jdbcTemplate; // since 2001
    private final EntityManager entityManager; // since 2006
    //    mybatis
    private final MyEntityRepo repo; // = Spring Data JPA, since 2011
    private final OtherClass other;

    @Transactional
    public void play() throws IOException {
        jdbcTemplate.update("insert into MY_ENTITY(id, name) values (100,?)", "SQL");
        altaMetoda();
        log.info("Inainte de SELECT");
        log.info("JPA NU TE MINTE: " + repo.count()); // JPA se stia vinovat ca inca n-a trimit cele 3 inserturi in DB => auto-flush inainte de select
//        repo.flush();// rar vazut
        if (repo.count() > 3) {
            log.info("TOTU BINE");
        }
        repo.save(new MyEntity("X"));
        log.info("Ies din metoda"); // JPA "Write Behind" = INSERTul de la repo.save se face dupa iesire, inainte de COMMIT
        // ❌ debug greu
        // ✅ mai putine round-tripuri la DB: (A) JDBC batch insert; (B) ca poate nu-i nevoie ca arunca runtime pana la final
    }
    private void altaMetoda() { // tranzactia pornita in caller method se continua aici
        MyEntity e = new MyEntity("JPA");
        repo.save(e); // ACUM JPA face e.setId(select nextval din seq)
        log.info("OAre are id deja? " + e.getId());
        repo.save(new MyEntity("JPA1"));
        repo.save(new MyEntity("JPA2"));
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MyEntityRepo repo;
}

// TODO
//  0 p6spy shows connection id, commit/rollback, actual query params (not ?) - everywhere < prod
//  1 rollback on runtime exception, commit on checked exception 🤬
//  2 Tx propagates with your calls (on thread)
//  3 @Tx on local method called within the same class - has no effect
//  4 Tx propagation control REQUIRES_NEW or NOT_SUPPORTED
//  5 Performance: JDBC connection starvation
