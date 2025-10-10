package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final JdbcTemplate jdbcTemplate; // 2001
  private final OtherClass other;

  // stores the tx in a ThreadLocal on the current thread (spring-web)
  // stores the tx in reactor-context (spring-webflux): TODO ask the reactor trainer
  @Transactional
  public void play() {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100, 'SQL' )");
    other.extracted();
  }
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final JdbcTemplate jdbcTemplate;
  @Transactional
  public void extracted() {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100, 'second' )");
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
