package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final DataSource dataSource; // 1998
  private final JdbcTemplate jdbcTemplate; // 2001
  private final EntityManager entityManager; // 2006
  private final MessageRepo repo; // = Spring Data JPA, 2011
  private final OtherClass other;


  @SneakyThrows
  @Transactional
  public void play() {
    // 1. helper al springului peste SQL ca sa nu pui tu mana pe Connection, DataSource, SQLException, .commit
    jdbcTemplate.update(
        "insert into MESSAGE(id, message) values (100,'SQL' )");

    // 2. JPA/Hibernate(ORM) folosit prin Spring Data JPA
    repo.saveAndFlush(new Message("JPA")); // nu pleaca nici un INSERT inca spre DB
    // hibernate face flush la entitati inainte de a face select
    repo.count(); // intai  flush=insert; SELECT COUNT(*) FROM MESSAGE
    log.info("Ies din metoda");
  } // dupa ce iesi din metoda te intorci la TransactionInterceptor cauzat de @Transactional

  // care incearca sa faca COMMIT la tot ce ai facut.
  // Atunci el insa se prinde ca JPA inca nu a facut flush la entitati in DB
  // si face intai inserturile in DB, apoi face commit-ul
  // de ce amana inseturile JPA-ul = "Write-Behind"
  // 1) ca poate nici nu nevoie sa le scrie (ca sare exceptie)
  // 2) pentru a le putea batheui impreuna (JDBC Batching) - cand importi fisiere


  @Transactional
  public void andrei() {
    log.info("Asta-i pentru Andrei");
    Message message = repo.findById(1L).orElseThrow();
    message.setMessage("Ceva Diferit");
    if (message.getMessage().contains(" ")) {//biz rule!
      throw new IllegalArgumentException("No spaces allowed");
    } else {
      repo.save(message);
    }
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
