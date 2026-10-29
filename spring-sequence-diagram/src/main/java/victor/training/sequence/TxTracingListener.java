package victor.training.sequence;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.trace.Span;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.TransactionExecution;
import org.springframework.transaction.TransactionExecutionListener;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Marks where each physical transaction begins, is suspended, resumes, commits or rolls back.
 * Attached to every {@code AbstractPlatformTransactionManager} bean — JDBC, JPA, JTA, yours —
 * by {@link SequenceAutoConfiguration}. The only class here that touches spring-tx.
 * <p>
 * Only transactions that actually <em>begin</em> are reported: a {@code @Transactional} method
 * that joins the caller's transaction (REQUIRED) begins nothing — so it draws no new frame.
 */
class TxTracingListener implements TransactionExecutionListener {
  private final SequenceRecorder recorder;
  private final AtomicLong ids = new AtomicLong();
  // the same TransactionStatus instance is passed at begin and at commit/rollback
  private final Map<TransactionExecution, Long> idByTransaction = Collections.synchronizedMap(new IdentityHashMap<>());

  TxTracingListener(SequenceRecorder recorder) {
    this.recorder = recorder;
  }

  @Override
  public void beforeBegin(TransactionExecution tx) { // before the connection is taken, so it lands inside the frame
    long id = ids.incrementAndGet();
    idByTransaction.put(tx, id);
    String flags = (tx.isReadOnly() ? "readOnly " : "") + (tx.isNested() ? "NESTED savepoint" : "");
    event(SequenceRecorder.TX_BEGIN, id, Attributes.builder()
        .put(SequenceRecorder.TX_NAME, shortName(tx.getTransactionName()))
        .put(SequenceRecorder.TX_FLAGS, flags.trim()));
  }

  @Override
  public void afterBegin(TransactionExecution tx, Throwable beginFailure) {
    Long id = idByTransaction.get(tx);
    if (id == null || beginFailure != null || !TransactionSynchronizationManager.isSynchronizationActive()) {
      return;
    }
    // REQUIRES_NEW and NOT_SUPPORTED park the current transaction: its synchronizations hear about it
    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
      @Override
      public void suspend() {
        event(SequenceRecorder.TX_SUSPEND, id, Attributes.builder());
      }

      @Override
      public void resume() {
        event(SequenceRecorder.TX_RESUME, id, Attributes.builder());
      }
    });
  }

  @Override
  public void afterCommit(TransactionExecution tx, Throwable commitFailure) {
    end(tx, commitFailure == null ? SequenceRecorder.TX_COMMIT : SequenceRecorder.TX_ROLLBACK);
  }

  @Override
  public void afterRollback(TransactionExecution tx, Throwable rollbackFailure) {
    end(tx, SequenceRecorder.TX_ROLLBACK);
  }

  private void end(TransactionExecution tx, String event) {
    Long id = idByTransaction.remove(tx);
    if (id != null) {
      event(event, id, Attributes.builder());
    }
  }

  private void event(String name, long id, AttributesBuilder attributes) {
    if (recorder.insideDiagram()) {
      recorder.event(Span.current(), name, attributes.put(SequenceRecorder.TX_ID, id).build());
    }
  }

  /** Whether the transaction the current thread runs in can now only roll back. */
  static boolean currentTransactionIsRollbackOnly() {
    if (!TransactionSynchronizationManager.isActualTransactionActive()) {
      return false;
    }
    try {
      return TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
    } catch (NoTransactionException e) {
      return false; // a transaction not started by @Transactional
    }
  }

  // "com.acme.OrderService.place" -> "OrderService.place"
  private static String shortName(String name) {
    if (name == null || name.isBlank()) return "programmatic";
    int method = name.lastIndexOf('.');
    int type = method > 0 ? name.lastIndexOf('.', method - 1) : -1;
    return name.substring(type + 1);
  }
}
