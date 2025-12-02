package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import javax.sql.DataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final DataSource dataSource; // 1998
  private final JdbcTemplate jdbcTemplate; // 2001
  private final EntityManager entityManager; // 2006
  private final MessageRepo repo; // = Spring Data JPA, 2011
  private final OtherClass other;

  @Transactional
  public void play() {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
    repo.save(new Message("JPA"));
  }
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
}

// TODO
//  0 p6spy shows connection id, commit/rollback, actual query params (not ?) - everywhere < prod
//  1 rollback on runtime exception, commit on checked exception 🤬
//  2 Tx propagates with your calls (on thread)
//  3 @Tx on local method called within the same class - has no effect
//  4 Tx propagation control REQUIRES_NEW or NOT_SUPPORTED
//  5 Performance: JDBC connection starvation
