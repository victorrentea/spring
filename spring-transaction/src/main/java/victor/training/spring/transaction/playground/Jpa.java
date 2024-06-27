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
//    Message m = new Message("ONE");
//    repo.save(m);
//    id = m.getId(); is same as:

    id = repo.save(new Message("ONE")).getId();
    log.info("End of method ---"); // TODO write-behind
  }

  public void two() {
//    Message e = repo.findById(id).orElseThrow();
//    e.setMessage("Different"); // TODO auto-flush changes

    // TODO lazy-loading
  }
}
