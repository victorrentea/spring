package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayLocks {
  private final MyEntityRepo repo;

  @SneakyThrows
  @Transactional
  public int thread() {
    MyEntity myEntity = repo.findById(1L).orElseThrow();
//    Message message = repo.findByIdLocking(1L).orElseThrow();
    log.info("START");
    Thread.sleep(100);
    log.info("END: {}", myEntity);
    return 0;
  }
}
