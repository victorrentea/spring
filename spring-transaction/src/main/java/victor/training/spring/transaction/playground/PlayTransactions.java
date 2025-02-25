package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import javax.sql.DataSource;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final MessageRepo repo;
  private final OtherClass other;

  @Transactional
  public void play() throws IOException {
    repo.save(new Message("JPA"));
//    throw new RuntimeException("💖causes a rollbacl");
    throw new IOException("😡causes a COMMIT (WTF!!?!?) - EJB starndard legacy");
    // to avoid this inherited mistake, NEVER THROW CHECKED EXCEPTIONS!
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
