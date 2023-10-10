package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional // e ca si cum pui pe TOATE METODELE din clasa
public class PlaygroundEx {
  private final MessageRepo repo;
  private final UnService other;

  // batch
  @SneakyThrows
  public void transactionOne()  {
    try {
      other.atomic();
    } catch (Exception e) {
     other. saveError(e);
     Thread.sleep(100);
      throw new RuntimeException(e);
    }
  }

  public void transactionTwo() {
    // intr-o zi apiul ala raspunde fff greu 1min ANAF
    String s = new RestTemplate().getForObject("http://.......", String.class); // Anti pattern sa faci req http in metoda @Transactional
    // 1 min tu ai luat 10% din capacitatea app tale (1/10 connec JDBC)
    // mai vin 9 calluri ca astea -> toate cele 10 conn sunt blocate pt 1 min
    // orice alt SELECT vrei sa faci in db vei ASTEPTA sa se elibere conexiune = JDBC Connection Pool Starvation
    repo.save(new Message("1"));
    repo.save(new Message("2"));
  }

}
// orice metoda chemata pastreaza by default tranzactia, mai putin daca
// - pune @Transactional(propagation = REQUIRES_NEW)
// - alt thread
@Service
@RequiredArgsConstructor
class UnService {
  private final MessageRepo repo;
//  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Async
  public void saveError(Exception e) {
    System.out.println("EU");
    repo.save(new Message("EROARE: " + e));
  }
  @Transactional
  public void atomic() {
    repo.save(new Message("insert transfer"));
    repo.save(new Message("insert audit"));
    throw new IllegalArgumentException("Biz");
  }
}
