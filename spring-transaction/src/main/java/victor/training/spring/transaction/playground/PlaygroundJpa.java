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
public class PlaygroundJpa {
  private final MessageRepo repo;
  private final OtherJpaClass other;

  public void transactionOne()  {
    repo.save(new Message("Ceva"));
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
