package victor.training.spring.transaction.playground.extra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.transaction.playground.Message;
import victor.training.spring.transaction.playground.MessageRepo;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBPrinter {
  private final MessageRepo repo;

  @Transactional(readOnly = true)
  public void print() {
    String contents = repo.findAll().stream().map(Message::toString).collect(joining("\n"));
    log.info("🏁🏁🏁 FINAL DATABASE CONTENTS:\n" + (contents.isEmpty()?"<EMPTY>":contents)+"""
       
       
       See more at  http://localhost:8080/h2-console/ using:
       URL= jdbc:h2:tcp://localhost:9092/~/test
       DRIVER= org.h2.Driver
       User Name= sa
       Password= sa""");
  }
}
