package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaygroundJpa {
  private final MessageRepo repo;
  private Long id;

  private String currentUser;
  private String stationId;

  @Transactional
  public void transactionOne() {
    id = repo.save(new Message("JPA")).getId();
  }

  @Transactional
  public void transactionTwo() {
  }
}
