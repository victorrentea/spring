package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayLocking {
  private final MessageRepo repo;

  @SneakyThrows
  @Transactional
  public int thread() {
    Message message = repo.findById(1L).orElseThrow();
    // Message message = repo.findByIdLocking(1L).orElseThrow();
    log.info("START");
    Thread.sleep(100);
    log.info("END: {}", message);
    return 0;
  }
}
