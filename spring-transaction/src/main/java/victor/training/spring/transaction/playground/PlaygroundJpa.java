package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class PlaygroundJpa {
  private final MessageRepo repo;
  private final OtherJpaClass other;

  @Transactional
  public void transactionOne()  {
//    TransactionTemplate tx;
//    tx.executeWithoutResult(s-> {
//      insert
//      insert
//    });
    repo.save(new Message("Ceva"));  //INSERT
    repo.saveAndFlush(new Message("Ceva")); //INSERT
    // fortezi un flush mai devreme: cu..
//    repo.saveAndFlush(new Message("Doi")); //INSERT
//    repo.flush(); // explicit call
//    System.out.println(repo.findByMessage("Doi"));// orice SELECT in DB hibernate il precedeaza cu un FLUSH (scrie iN DB tot ce mai avea de scris)
    System.out.println("Ies din functie: rabbit.send(), kafka.send() --- ");
  }
  @Transactional
  public void transactionTwo() {
    Message message = repo.findById(1L).orElseThrow();
    message.setMessage("altu"); //SOC: modificarea unei @Entity in cadrul unei metode @Transactional ajunge automat UPDATE in DB
  }
}
@Service
@RequiredArgsConstructor
class OtherJpaClass {
  private final MessageRepo repo;
}
