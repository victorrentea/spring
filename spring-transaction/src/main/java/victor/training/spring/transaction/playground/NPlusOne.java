package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NPlusOne {
  private final MyEntityRepo repo;

  public void insert() {
    for (int i = 0; i < 10; i++) {
      repo.save(new MyEntity()
              .addTag("tag1")
              .setName("Entity " + i));
    }
  }
  @Transactional(readOnly = true)
  void export() {
    for (MyEntity entity : repo.findAll()) { // 1
      log.info("Entity in CSV: " + entity.getName() + ";" + entity.getTags()); //N
    }
  }
}
