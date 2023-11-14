package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

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
  public void play() {
    // NU BLOCA:
    // - synchronized { }
    // - WSDL call
    // - restApi.call(); // 100ms - 5 sec -> DOAMNE FERESTE! NU bloca thread-ul cand ai o tranzactie deschisa
    try {
      other.atomicStuff();
    } catch (Exception e) {
      other.saveInNewTx(e);
    }
  }




  // COMMIT daca tot ok; connection.commit();
  // sau ROLLBACK daca exceptioe; connection.rollback();
  // cand faci connection.close() pe o connex JDBC, daca acea conex a venit
  // dintr-un pool de conexiuni, close() doar inseamna release() -> pune-o inapoi in pool,
  // s-o foloseasca alt request dupa time
  //==> din punctul de vedere al DB e aceeasi conexiune (NLS_LANG)
}
// in cod :
// A) JdbcTemplate -> PL/SQL 6/7
// B) EntityManager 2/7
// C) Spring Data 1/7
// D) Jooq (DSL)

//OJDBC7 + spark
@Slf4j
@Service
@RequiredArgsConstructor
class OtherClass {
  private final JdbcTemplate jdbcTemplate;
  @Transactional
  public void atomicStuff() throws IOException {
    log.info("INSERT#1");
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
    altaMetoda();
  }

  @Transactional(rollbackFor = Exception.class) // are sens: ATOMIC intre cele 2 insert-uri
  public void altaMetoda() throws IOException {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (101,? )", "SQL2"); // UK violation
    jdbcTemplate.update("insert into MESSAGE(id, message) values (103,? )", "suchili");
//    throw new RuntimeException("Oups! Business exception"); // unchecked
    // moment penibil pt Spring: exceptiile checked lasa tranzactia sa COMMITa
    throw new IOException("exceptie rea, greseli in Java. Doar Java are asa mizerii");  // checked
    // de ce asa?
    // Spring a furat developeri din JavaEE/EJB1.1, si ca sa faca usoara tranzitia la Spring,
    // au copia comportamentul @TransactionAttribute (EJB) in @Transactional, cu tot cu propagation=
    // si exception handling (tampenia asta)
    // Concluzie: NICIODATA sa nu arunci exceptii cu throws din metodele tale.
  }
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveInNewTx(Exception e) {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (105,'EROARE:' || ? )", e.getMessage());
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
