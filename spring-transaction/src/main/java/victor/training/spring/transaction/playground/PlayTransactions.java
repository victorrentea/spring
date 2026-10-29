package victor.training.spring.transaction.playground;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final DataSource dataSource; // since 1998
  private final JdbcTemplate jdbcTemplate; // since 2001
  private final EntityManager entityManager; // since 2006
  private final MyEntityRepo repo; // = Spring Data JPA, since 2011
  private final OtherClass other;

  @Transactional
  public void play() {
    other.extracted();
    repo.save(new MyEntity("JPA4"));
    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (100,'SQL')");
  }
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MyEntityRepo repo;
//  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void extracted() {
    CompletableFuture.runAsync(() ->
      repo.save(new MyEntity("JPA_separat"))
    );
  }
}

// TODO
//  0 p6spy shows connection id, commit/rollback, actual query params (not ?) - everywhere < prod
//  1 rollback on runtime exception, commit on checked exception 🤬
//  2 Tx propagates with your calls (on thread)
//  3 @Tx on local method called within the same class - has no effect
//  4 Tx propagation control REQUIRES_NEW or NOT_SUPPORTED
//  5 Performance: JDBC connection starvation
