package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  private final JdbcTemplate jdbcTemplate;
  private final OtherClass other;

//  @TransactionAttribute (ejb)
  @Transactional
  public void transactionOne()  {
    repo.sqlNativ();
    other.f();
//      throw new IllegalArgumentException(); // runtime
    try {
      new FileInputStream("a.dat");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
//      throw new MyException(ErrorCode.NASPA, e); // back to the future: 2023 anu Domnului
    }
  }
  public void transactionTwo() {}
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void f() {
    repo.save(new Message("Job failed with status : ... ")); // JPA insert
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
