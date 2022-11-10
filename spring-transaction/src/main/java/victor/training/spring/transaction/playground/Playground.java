package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo; //Data= Spring Data (interfete fara implem care extind JpaRepository)
    private final JdbcTemplate jdbcTemplate; // SQL murdar

    private final OtherClass other;
    // 0 p6spy✅
    // 1 Cause a rollback by breaking NOT NULL✅
    // spring data JPA: @Query, metode cu nume destepte, native, @Modifying

    //  , throw Runtime, throw CHECKED
    // 2 Tx propagates with your calls (in your thread😱)
    // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
    // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
    // 5 Performance: connection starvation issues : debate: avoid nested transactions

    @Transactional
    public void transactionOne() {
        repo.save(new Message("unu"));
        other.metoda();

        repo.flush();// anti-pattern: performanta--,
        // TODO kafka/rabbit.send intr-un @TransactionalEventListener(phase=AFTER_COMMIT)

//        System.out.println("toate="+ repo.findAll());
        System.out.println("toate="+ repo.findByMessage("aa"));
        // SELECT in DB il obliga pe Hib sa flushuie tot ce avea de scris pentru ca rezultatele tale sa fie corecte.

        log.info("Ies din metoda");
        // [Write-Behind] JPA face INSERTURILE dupa ce iese din functie. cand tu faci .save() JPA doar pune in PersistenceContext
        // daca faceai 1M de repo.save() => write behind i-ar da voie lui Hib sa faca BATCHING de inserturi la final
        // DRAMA: calci un UQ.cand iti sare exceptia?
    }

    @Transactional
    public void transactionTwo() {
    }
}
@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
    @Transactional // asta nu creeaza tx noua ci refoloseste => nu face COMMIT dupa la finalul metodei!
    public void metoda() {
        repo.save(new Message("unu"));
    }
}