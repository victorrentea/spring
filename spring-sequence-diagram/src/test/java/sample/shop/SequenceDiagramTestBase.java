package sample.shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;
import victor.training.sequence.DiagramStore;
import victor.training.sequence.SequenceDiagram;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The same scenarios on every database a subclass names: nothing in the module may depend on one.
 * The service is called straight from the test, so each call is a top-level call — a diagram of its own.
 */
@SpringBootTest(classes = ShopApp.class)
abstract class SequenceDiagramTestBase {
  @Autowired
  OrderService orders;
  @Autowired
  DiagramStore diagrams;

  @Test
  void requiresNewSuspendsTheCallerTransactionAndResumesIt() {
    orders.place("book");

    assertThat(lastDiagram())
        .contains("<b>tx#1</b> · @Transactional OrderService.place — ✅ COMMIT")
        .contains("<b>tx#2</b> · @Transactional AuditService.log · tx#1 suspended — ✅ COMMIT")
        .contains("<b>tx#1</b> (resumed)")
        .contains("<b>INSERT</b> into orders(item) values ('book')")
        .contains("<b>INSERT</b> into audit_log(message) values ('placed book')")
        .contains("setAutoCommit(false)", "<b>COMMIT</b>", "getConnection() from pool");
  }

  @Test
  void aRuntimeExceptionRollsBack() {
    assertThatThrownBy(() -> orders.placeAndFail("pen")).isInstanceOf(IllegalStateException.class);

    assertThat(lastDiagram())
        .contains("OrderService.placeAndFail — <color:#C62828>❌ ROLLBACK")
        .contains("<b>ROLLBACK</b>")
        .contains("⚡ IllegalStateException");
  }

  @Test
  void anExceptionThroughAJoinedProxyDoomsTheCallerTransaction() {
    assertThatThrownBy(() -> orders.swallowJoinedFailure("ink")).isInstanceOf(UnexpectedRollbackException.class);

    assertThat(lastDiagram())
        .contains("☠ tx#1 marked <b>rollback-only</b>")
        .contains("❌ ROLLBACK")
        .contains("⚡ UnexpectedRollbackException")
        .doesNotContain("tx#2"); // REQUIRED joins: no transaction of its own
  }

  @Test
  @SequenceDiagram
  void theAnnotatedTestBodyIsOneDiagram() {
    orders.place("cup");
    orders.place("mug");
  }

  @AfterEach
  void annotatedTestWasDrawn(TestInfo test) { // runs after the extension closed the test's root span
    if (test.getTestMethod().orElseThrow().isAnnotationPresent(SequenceDiagram.class)) {
      assertThat(diagrams.recent().get(0).name()).isEqualTo(getClass().getSimpleName() + ".theAnnotatedTestBodyIsOneDiagram");
      assertThat(lastDiagram())
          .contains("actor \"Test\"")
          .contains("values ('cup')", "values ('mug')");
    }
  }

  private String lastDiagram() {
    return diagrams.recent().get(0).puml();
  }
}
