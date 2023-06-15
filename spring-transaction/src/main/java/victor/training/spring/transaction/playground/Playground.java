package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager jpaCurat;

    @Transactional
    public void transactionOne() {
        repo.suchili("SQL NATIV");
        repo.save(new Message("SQL a")); // asta nu trimite in DB inca INSERTUL pana cand nu
        repo.save(new Message("SQL b")); // asta nu trimite in DB inca INSERTUL pana cand nu
//        repo.saveAndFlush(new Message("SQL NATIV")); // daca vrei sa dizablezi write behind, si sa trimit INSERT in DB pe loc, dar poti pierde performanta
        // se face FLUSH

        log.info("Write-behind (JPA) = ce aveai de scris in DB se flush() abia la finalul tx inainte de COMMIT");
    } // proxy face flush > commit

    @Transactional
    public void transactionTwo() {
        System.out.println(repo.findByMessageContainingIgnoreCase("lO"));
        System.out.println(repo.findByMessageLike("LO"));
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
}