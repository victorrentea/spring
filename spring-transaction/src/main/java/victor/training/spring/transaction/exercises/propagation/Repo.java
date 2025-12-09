package victor.training.spring.transaction.exercises.propagation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional
public class Repo {
  public void r1() {
    log.info("r1");
  }
}
