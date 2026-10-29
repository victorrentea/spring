package victor.training.spring.transaction.exercises.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import victor.training.sequence.SequenceDiagram;

import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Draws what the exercises do right now: solve one, re-run, and watch its frame change colour.
 */
@SequenceDiagram
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:exceptions;DB_CLOSE_DELAY=-1")
class ExceptionsSequenceTest {
  @Autowired
  Caller caller;

  @Test
  void runtimeExceptions_markCurrentTransactionForRollback() {
    catchThrowable(() -> caller.runtimeExceptions_markCurrentTransactionForRollback());
  }

  @Test
  void rollbackForChecked() {
    catchThrowable(() -> caller.rollbackForChecked());
  }

  @Test
  void runtimeException_ifUnseenByProxy_doesntMarkForRollback() {
    catchThrowable(() -> caller.runtimeException_ifUnseenByProxy_doesntMarkForRollback());
  }

  @Test
  void separateTransaction_isRolledbackIndependently() {
    catchThrowable(() -> caller.separateTransaction_isRolledbackIndependently());
  }
}
