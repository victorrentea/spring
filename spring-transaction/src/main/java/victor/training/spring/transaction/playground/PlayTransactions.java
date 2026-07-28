package victor.training.spring.transaction.playground;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final EntityManager entityManager; // since 2006
  private final MyEntityRepo repo; // = Spring Data JPA, since 2011
  private final OtherClass other;
  private final JdbcTemplate jdbcTemplate;

  @Transactional
  public void play() throws IOException {
    try {
      repo.save(new MyEntity("JPA1"));
      repo.save(new MyEntity("JPA2"));
      System.out.println(1/0); // nu cauzeaza rollback
    } catch (Throwable e) {
      log.error("Shawarma", e);
    }
    try {
      other.extracted();
    } catch(RuntimeException e) { // dar asta da
      log.info("Ignoring: " + e);
      //TODO am dat mail la biz " ca ce fac pe erori" in Jun 2022
    }
  }
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final MyEntityRepo repo;
  //(propagation = Propagation.REQUIRES_NEW) //bad practice - evitati; complex de inteles, greu de testat
  @Transactional //~> la trecerea exceptiei runtime prin proxyul asta, se marcheaza neresuscitabil tx activa ca "rollback-only"
  public void extracted() { // to pe threadul ala ai ramas, si save @Transactional se enlisteaze in tx activa pe thread = deci tot aia
    repo.save(new MyEntity("JPA3"));
    if (true) throw new RuntimeException("BUG🐞");
  }
}

// TODO
//  0 p6spy shows connection id, commit/rollback, actual query params (not ?) - everywhere < prod
//  1 rollback on runtime exception, commit on checked exception 🤬
//  2 Tx propagates with your calls (on thread)
//  3 @Tx on local method called within the same class - has no effect
//  4 Tx propagation control REQUIRES_NEW or NOT_SUPPORTED
//  5 Performance: JDBC connection starvation
