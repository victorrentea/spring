package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaygroundEx {
  private final MessageRepo repo;
  private final UnService other;

  // batch
  @Transactional
  public void transactionOne()  {
    try {
      other.atomic();
    } catch (Exception e) {
      saveError(e);
      throw new RuntimeException(e);
    }
  }
  // 👑 TZEAPA nr1: daca chemi o metoda in aceeasi clasa nu se mai aplica @Transactional
  // pt ca proxyurile nu functioneaza daca chemi metoda local
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveError(Exception e) {
    System.out.println("EU");
    repo.save(new Message("EROARE: " + e));
  }
}
@Service
@RequiredArgsConstructor
class UnService {
  private final MessageRepo repo;
  @Transactional
  public void atomic() {
    repo.save(new Message("insert transfer"));
    repo.save(new Message("insert audit"));
    throw new IllegalArgumentException("Biz");
  }
}
