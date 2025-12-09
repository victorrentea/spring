package victor.training.spring.transaction.playground.extra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.transaction.playground.MyEntity;
import victor.training.spring.transaction.playground.MyEntityRepo;

import static java.util.stream.Collectors.joining;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBPrinter {
  private final MyEntityRepo repo;

  @Transactional(readOnly = true)
  public void print() {
    String contents = repo.findAll().stream().map(MyEntity::toString).collect(joining("\n"));
    log.info("🏁🏁🏁 FINAL DATABASE CONTENTS:      --- you can see more at http://localhost:8080/h2-console/ using \nURL: jdbc:h2:tcp://localhost:9092/~/test  DRIVER: org.h2.Driver USER: sa PASS: sa \n"
            + (contents.isEmpty() ? "<EMPTY>" : contents));
  }
}
