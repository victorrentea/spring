package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  // tx management low level:
//    DataSource ds;
//    Connection connection = ds.getConnection();
//    connection.setAutoCommit(false); // open a TX
//    // INSERT ,,, your code
//    connection.commit();

//  @Transactional //
  // acquire a JDBC Connection from the connection pool (size=10) Hikari
  // bind the connection to the CURRENT THREAD until the end of the method
  // creating a PersistenceContext on the THREAD
  public void transactionOne() {
    var dataFromRemote = "data retrieved via an API call";
    // restTemplate.getForObject / webClient.block() => JDBC Connection Pool Starvation

    other.atomicPart(dataFromRemote);
  }

  @Autowired
  private OtherClass other; // proxy injected here !

  public void transactionTwo() {}
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  @Transactional
  public void atomicPart(String dataFromRemote) {
    repo.save(new Message("JPA with " + dataFromRemote));
    repo.save(new Message(null));
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
