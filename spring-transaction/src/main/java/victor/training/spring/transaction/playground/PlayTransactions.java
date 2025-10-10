package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import javax.sql.DataSource;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private static final Logger log = LoggerFactory.getLogger(PlayTransactions.class);
  private final JdbcTemplate jdbcTemplate; // 2001
  private final OtherClass other;

  // stores the tx in a ThreadLocal on the current thread (spring-web)
  // stores the tx in reactor-context (spring-webflux): TODO ask the reactor trainer
  @Transactional
  public void play() throws IOException {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100, 'SQL' )");
    try {
      other.extracted();
    }catch(Exception e) {
      log.warn("Ignoring: "+ e);
      throw new IOException(e);
    }
    System.out.println("Exiting method");
  }

}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final JdbcTemplate jdbcTemplate;
//  @Transactional(propagation = Propagation.NOT_SUPPORTED)// suspends the old, and enters the method with NO TX
  @Transactional(propagation = Propagation.REQUIRES_NEW)
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
