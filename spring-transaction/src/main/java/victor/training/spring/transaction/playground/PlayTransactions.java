package victor.training.spring.transaction.playground;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final JdbcTemplate jdbcTemplate; // since 2001
  private final OtherClass otherClass;
  @Transactional 
  public void play() {
    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (100,'SQL')");
    otherClass.second();
  }
//  @Transactional //~= @TransactionAttribute(from EJB)
//  public void play() throws IOException {
//    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (100,'SQL')");
//    if(true) throw new IOException("Known 💩 in Java. 😱😱 COMMIT UNTIL HERE! WTFeat?");
//    // when Spring was born, everyone was using EJB (massive and intrusive) ~2005
//    // what todo? ⇒ NEVER EVER EVER use 'throws' in methods of Spring beans (mistake anyway)
//    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (101,'SQL2')");
//    if(true) throw new IllegalArgumentException("rollsback the transaction");
//  }
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final JdbcTemplate jdbcTemplate;
  protected void second() {
    jdbcTemplate.update("insert into MY_ENTITY(id, name) values (101,'SQL2')");
  }
}








// TODO
//  0 p6spy shows connection id, commit/rollback, actual query params (not ?) - everywhere < prod
//  1 rollback on runtime exception, commit on checked exception 🤬
//  2 Tx propagates with your calls (on thread)
//  3 @Tx on local method called within the same class - has no effect
//  4 Tx propagation control REQUIRES_NEW or NOT_SUPPORTED
//  5 Performance: JDBC connection starvation
