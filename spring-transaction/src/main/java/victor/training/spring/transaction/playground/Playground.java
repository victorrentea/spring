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
  private final JdbcTemplate jdbcTemplate;

  @Transactional
  public void play() {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
    anotherMethodICall();
  }

  @Transactional // COMPLETELY USELESS as all calls to a private method go within the same class -> can't be proxied.
  public void anotherMethodICall() {
    // the fact that in the log you see "connection 0" for both INSERT => they run on the same JDBC transaction
    jdbcTemplate.update("insert into MESSAGE(id, message) values (101,'SQL' )");
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
