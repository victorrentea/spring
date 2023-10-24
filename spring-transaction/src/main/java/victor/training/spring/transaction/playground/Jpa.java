package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class Jpa {
  private final MessageRepo repo;
  private Long id;

  @Transactional
  public void one() {
//    id = repo.save(new Message("ONE")).getId();
    System.out.println("End of method");
  }

  @Transactional
  public void two() {
//    Message e = repo.findById(id).orElseThrow();
//    e.setMessage("Different");

    // TODO lazy loading
  }
}
