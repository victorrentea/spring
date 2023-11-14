package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  private final EntityManager entityManager;
  private final JdbcTemplate jdbcTemplate;
  private final OtherClass other;

  @Transactional // Spring manageuieste tranzactia cu o baza de date SQL
  // connection = dataSource.getConnection();
  // START TX = connection.setAutoCommit(false);
  public void play() {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
    jdbcTemplate.update("insert into MESSAGE(id, message) values (101,'SQL' )"); // UK violation
  }
  // COMMIT daca tot ok; connection.commit();
  // sau ROLLBACK daca exceptioe; connection.rollback();
}
// in cod :
// A) JdbcTemplate -> PL/SQL
// B) EntityManager
// C) Spring Data
// D) Jooq (DSL)

//OJDBC7 + spark






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
