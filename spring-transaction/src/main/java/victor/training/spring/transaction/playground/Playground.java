package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class Playground {
  private static final Logger log = LoggerFactory.getLogger(Playground.class);
  private final MessageRepo repo;
  private final EntityManager entityManager;
  private final JdbcTemplate jdbcTemplate;
  private final OtherClass other;

  @Transactional
  public void play() {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
    try {
      jdbcTemplate.update("insert into MESSAGE(id, message) values (101,'SQL' )");
    }catch(Exception e) { // if the exception never gets out of the method, the transaction is not rolled back -> COMMITS
      log.error("Exception", e);
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
