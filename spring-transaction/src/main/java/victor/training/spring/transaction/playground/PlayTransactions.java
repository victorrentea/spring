package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import victor.training.spring.transaction.TransactionalMindit;

import javax.sql.DataSource;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final JdbcTemplate jdbcTemplate; // 2001
  private final EntityManager entityManager; // 2006
  private final MessageRepo repo; // = Spring Data JPA, 2011
  private final OtherClass other;

//  @Transactional(rollbackFor = Exception.class) // ~ @TransactionAttribute (EJB)
  @TransactionalMindit
  public void play() throws IOException {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
    jdbcTemplate.update("insert into MESSAGE(id, message) values (101,'SQL2' )");
    if (true) throw new IOException("Atomic pana acolo");
    repo.save(new Message("JPA"));
  }
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
}

// TODO
//  0 p6spy
//  1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
//  2 Tx propagates with your calls (in your thread😱)
//  3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
//  4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
//  5 Performance: connection starvation issues : debate: avoid nested transactions
