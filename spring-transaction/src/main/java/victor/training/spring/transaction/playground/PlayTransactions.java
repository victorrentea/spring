package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
  @Transactional
public class PlayTransactions {
  private final DataSource dataSource; // 1998
  private final JdbcTemplate jdbcTemplate; // 2001
  private final EntityManager entityManager; // 2006
  private final MessageRepo repo; // = Spring Data JPA, 2011
  private final OtherClass other;
// anii 2000
//    Connection connection = dataSource.getConnection();
//    connection.setAutoCommit(false); // adica nu scrie direct in DB ce INSERT/UPDATE/DELETE
//  try{
//    f(connection);
//    connection.createStatement().executeQuery("INSERT")
//    connection.createStatement().executeQuery("INSERT")
//    connection.commit();
//  }catch(Exception e){
//      connection.rollback();
//    }
//  finally {
//    connection.close();
//  }

  // proxyul deschide tx pe conex luata din JDBC conn pool inainte de intrarea in metoda
  public void play(String nume) {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,?)",nume);
    extracted();
    // orice cod executi in timpul metodei @Transactional va fi in tranzactie cu tine (by default)
    throw new RuntimeException("BUM");
  }
  // proxyul din fata metodei face COMMIT automat dupa iesirea din metoda

  private void extracted() {
    // INSERTUL asta merge in baza pe aceeasi connex ca INSERTul de mai sus.
    // Conex JDBC ramane agatata de Threadul curent
    extracted1();
  }

  private void extracted1() {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (101,'SQL2')");
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
