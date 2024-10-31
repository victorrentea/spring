package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final DataSource dataSource; // 1998
  private final JdbcTemplate jdbcTemplate; // 2001
  private final EntityManager entityManager; // 2006
  private final MessageRepo repo; // = Spring Data JPA, 2011
  private final OtherClass other;


  @SneakyThrows
  @Transactional
  public void play() {
    Connection connection = dataSource.getConnection();  // conn poate fi folosita doar
    // din threadul care a obtinut-o (thread-bound)
    connection.setAutoCommit(false); // = start Tx
    CompletableFuture.runAsync(() -> {
//      repo.save(new Message("JPA1")); // fol connex #1
      try {
        connection.createStatement()
            .execute("insert into MESSAGE(id, message) values (100,'JDBC' )");
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    });
    CompletableFuture.runAsync(() -> {
//      repo.save(new Message("JPA2")); // fol connex #2
      try {
        connection.createStatement()
            .execute("insert into MESSAGE(id, message) values (100,'JDBC' )");
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    });
    connection.commit(); // = end Tx






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
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
