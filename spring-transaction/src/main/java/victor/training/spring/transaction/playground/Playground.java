package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  private final OtherClass other;

  // Spring Data JPA: ✅JpaRepository + @Query
  // JPA(Hibernate)  Session🪦, EntityManager🪦, @Entity✅
  // JdbcTemplate 2010 imbraca SQL nativ - ✅ DOAR daca nu ai JPA
  // JDBC🪦 90' Connection, ResultSet, PreparedStatement
//  @TransactionAttribute
  @Transactional // nu e necesar daca faci un singur .save in metoda
  public void transactionOne() {
    // orice metoda chemata mai jos de aceasta metoda va 'propaga' tranzactia.
    // CUM PUII🐣 MEI SE FACE ASTA ?
    //   !!! In java exista variabile magice "ThreadLocal" care pot tine
    // date specifice threadului curent < acolo sta tranzactia pornita de @Transactioanl
    repo.save(new Message("JPA"));
    other.pasu2();
  }

  public void transactionTwo() {}
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  public void pasu2() {
    repo.save(new Message("NULL"));
    if (true) throw new IllegalArgumentException("Ceva de biz");
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
