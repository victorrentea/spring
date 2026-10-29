package victor.training.spring.transaction.exercises.propagation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import victor.training.sequence.SequenceDiagram;

/**
 * BEFORE WHAT METHOD DOES THE TRANSACTION START? — the answers, drawn:
 * open target/sequence/index.html after running this.
 */
@SequenceDiagram
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:propagation;DB_CLOSE_DELAY=-1")
class PropagationSequenceTest {
  @SpringBootApplication
  static class App { // the exercise package has no @SpringBootApplication of its own
  }

  @Autowired
  AService service;

  @Test
  void flow1_a1() {
    service.a1();
  }

  @Test
  void flow2_a2() {
    service.a2();
  }

  @Test
  void flow4_a4_requiresNew() {
    service.a4();
  }

  @Test
  void flow5_a5_notSupported() {
    service.a5();
  }

  @Test
  void flow6_a6_notSupported() {
    service.a6();
  }
}
