package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.concurrent.CompletableFuture;

@Slf4j
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
    log.info("INSERT#1");
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
    other.altaMetoda();
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

@Slf4j
@Service
@RequiredArgsConstructor
class OtherClass {
  private final JdbcTemplate jdbcTemplate;
  @Async // muta executia metodei pe alt thread, prin intermediul unui proxy
  public void altaMetoda() {// orice metoda chemi in acelasi thread ramane in aceeasi tranzactie
    // tranzactia|JDBC Connection sunt BOUND pe thread
    log.info("INSERT#2");

    // acum acest insert merge in DB fara tx, pe auto-commit true
    jdbcTemplate.update("insert into MESSAGE(id, message) values (101,? )", "JPA"); // UK violation
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
