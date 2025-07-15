package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayTransactions {
  private final JdbcTemplate jdbcTemplate; // 2001
  private final MessageRepo repo; // = 💖 Spring Data JPA, 2011
  private final OtherClass other;

  @Transactional
  public void play() {
    repo.save(new Message("JPA"));
    try {
      other.extracted();
    } catch (Exception e) {
      // nimic: sa ma caute colegii cu drujba mai tarziu
    }
    log.info("Ies din functie");
  }
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;

  public void extracted() {
    repo.saveAndFlush(new Message("JPA2"));
    throw new RuntimeException();
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
