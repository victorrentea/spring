package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  private final OtherClass other;

  // Spring Data JPA: ✅JpaRepository + @Query
  // JPA(Hibernate)  Session🪦, EntityManager🪦, @Entity✅
  // JdbcTemplate 2010 imbraca SQL nativ - ✅ DOAR daca nu ai JPA
  // JDBC🪦 90' Connection, ResultSet, PreparedStatement

  @Transactional
  public void transactionOne() {
    repo.queryNativ();
    repo.save(new Message(null));
  }

  public void transactionTwo() {}
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
