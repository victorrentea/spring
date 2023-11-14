package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  private final EntityManager entityManager;
  private final JdbcTemplate jdbcTemplate;
  private final OtherClass other;


  // Spring manageuieste tranzactia cu o baza de date SQL
  // connection = dataSource.getConnection();
  // START TX = connection.setAutoCommit(false);
  @Transactional
  public void play() {
    // NU BLOCA:
    // - synchronized { }
    // - WSDL call
    // - restApi.call(); // 100ms - 5 sec -> DOAMNE FERESTE! NU bloca thread-ul cand ai o tranzactie deschisa
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
    jdbcTemplate.update("insert into MESSAGE(id, message) values (101,'SQL' )"); // UK violation
  }
  // COMMIT daca tot ok; connection.commit();
  // sau ROLLBACK daca exceptioe; connection.rollback();
  // cand faci connection.close() pe o connex JDBC, daca acea conex a venit
  // dintr-un pool de conexiuni, close() doar inseamna release() -> pune-o inapoi in pool,
  // s-o foloseasca alt request dupa time
  //==> din punctul de vedere al DB e aceeasi conexiune (NLS_LANG)
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
