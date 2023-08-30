package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  private final EntityManager entityManager;
  private final JdbcTemplate jdbc;
  private final OtherClass other;

  @Transactional
  public void transactionOne() {
    repo.save(new Message("JPA"));
    repo.save(new Message("BIS"));
    System.out.println("ies din metoda");
  } // WRITE-BEHIND = la inchiderea unei @Transactional JPA se uita in PersistenceContext dupa 'schimbari ce trebuie flush-uite
  // (scrise in DB)=> PersistenceContext functioneaza ca "buffer de scriere".
  // DE CE ?
  // 1) poate nu-i nevoie sa fac inser ca face tx rollback
  // 2) POT batchui odata mai multe INSERTuri la nivel de JDBC

//  @Transactional
  public void transactionTwo() {
    Message message = repo.findById(1L).orElseThrow();
    message.setMessage("Altu");
    repo.save(message); // oricum face auto-flush(UPDATE) daca ai modificat entitatea intr-o @Transaction
  }
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
