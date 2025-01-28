package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final JdbcTemplate jdbcTemplate; // 2001
  private final MessageRepo repo; // = Spring Data JPA, 2011
  private final OtherClass other;

  @Transactional
      (rollbackFor = Exception.class)  // 1 fix, pentru cine stie
  // 2, mai bine renunta sa mai arunci vreodata exceptii CHECKED. oricum sunt greseli in limbaj
  public void play() throws IOException {
    chemataLocal();
    // o tx face rollback daca sare orice exceptie din metoda @Transactional
    // de ex cand oricare din cele 2 queruri crapa
  }

  private void chemataLocal() throws IOException { // sonar se prinde
    repo.insert("SQL");
    repo.save(new Message("SQL2"));
//    if (Math.random()<1)throw new IllegalArgumentException("ghinion");
    if (Math.random()<1) throw new IOException("N-am gasit un fisier. ca-n anii 90'"); // exceptie d-aia scarboasa de doar Java are
    // soc: da commit ca asa se comprta si batranu EJB (Java EE) de udne a furat SPring suflete.
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
