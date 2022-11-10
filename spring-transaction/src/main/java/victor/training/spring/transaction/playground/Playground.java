package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;

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

        new Thread(()->other.metoda()).start();
        // cand pleci de pe un thread NU POTI lua cu tine JDBC Connection curent
        // pt ca JDBC Conencton sta si el pe THREAD.
        // Tranzactia de DB de fapt e pornita pe JDBC COnnection
//        Connection connection;
//        connection.setAutoCommit(false); // asa porneam tx 15 in urma
//        connection.commit(); // COMMIT
//        connection.rollback();
    }

    @Transactional
    public void transactionTwo() {
    }
}
@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
    @Transactional
    // acum proxyul acesta a fost nevoit sa-si ia connex JDBC noua pe care sa faca start/commit TX.
    public void metoda() {
        repo.save(new Message("doi"));
    }
}