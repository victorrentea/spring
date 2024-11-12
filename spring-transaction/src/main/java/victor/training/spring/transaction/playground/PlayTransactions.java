package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PlayTransactions {
//  private final DataSource dataSource; // 1998
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
  @Transactional  //(rollbackFor = Exception.class)// Fix#1
  public void play(String nume) throws IOException {
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,?)",nume);
    CompletableFuture.runAsync(() -> other.extracted());
//    throw new IOException("BUM"); // exceptie checked da commit . CE BOU A FACUT ASTA?// comportament preluat din EJB - greselile tineretii te bantuie la batranete
    throw new RuntimeException("BUM");
    //  runtime da rollback
    // Fix#2 de azi pana la pensie NU MAI ARUNCA NICIODATA EXCEPTII CHECKED
    // oricum sunt greseli in limbajul java. *nici un alt limbaj nu are exceptii checked*
  }
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  private final JdbcTemplate jdbcTemplate;

  // proxyul din fata metodei face COMMIT automat dupa iesirea din metoda
  @Transactional // e pus prost aici ca e chemat local in aceeasi clasa
  public void extracted() {
    // INSERTUL asta merge in baza pe aceeasi connex ca INSERTul de mai sus.
    // Conex JDBC ramane agatata de Threadul curent
    jdbcTemplate.update("insert into MESSAGE(id, message) values (101,'SQL2')");
    jdbcTemplate.update("insert into MESSAGE(id, message) values (101,'SQL2')");
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
