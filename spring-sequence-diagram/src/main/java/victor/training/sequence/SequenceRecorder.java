package victor.training.sequence;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;

import java.util.concurrent.atomic.AtomicLong;

import static io.opentelemetry.api.common.AttributeKey.longKey;
import static io.opentelemetry.api.common.AttributeKey.stringKey;

/**
 * Records what a sequence diagram needs as OpenTelemetry spans and span events.
 * <p>
 * Every start, end and event is stamped with a tick from one counter: span timestamps can tie
 * (a COMMIT and the frame closing around it happen in the same microsecond), the tick never does.
 */
public class SequenceRecorder {
  // what a span stands for in the diagram
  public static final AttributeKey<String> KIND = stringKey("seq.kind");
  public static final String KIND_TEST = "test";
  public static final String KIND_HTTP = "http";
  public static final String KIND_CALL = "call";
  public static final String KIND_DB = "db";

  public static final AttributeKey<String> PARTICIPANT = stringKey("seq.participant");
  public static final AttributeKey<String> LABEL = stringKey("seq.label");
  public static final AttributeKey<String> ERROR = stringKey("seq.error");
  public static final AttributeKey<Long> HTTP_STATUS = longKey("http.response.status_code");
  public static final AttributeKey<String> DB_OPERATION = stringKey("db.operation");
  public static final AttributeKey<String> DB_STATEMENT = stringKey("db.statement");

  public static final AttributeKey<Long> TICK_START = longKey("seq.tick.start");
  public static final AttributeKey<Long> TICK_END = longKey("seq.tick.end");
  public static final AttributeKey<Long> TICK = longKey("seq.tick");

  // span events marking the transaction lifecycle
  public static final String TX_BEGIN = "tx.begin";
  public static final String TX_SUSPEND = "tx.suspend";
  public static final String TX_RESUME = "tx.resume";
  public static final String TX_COMMIT = "tx.commit";
  public static final String TX_ROLLBACK = "tx.rollback";
  public static final String TX_ROLLBACK_ONLY = "tx.rollback-only";
  public static final AttributeKey<Long> TX_ID = longKey("tx.id");
  public static final AttributeKey<String> TX_NAME = stringKey("tx.name");
  public static final AttributeKey<String> TX_FLAGS = stringKey("tx.flags");

  private final AtomicLong ticks = new AtomicLong();
  private final Tracer tracer;

  public SequenceRecorder(Tracer tracer) {
    this.tracer = tracer;
  }

  /** The span every diagram hangs from: a test method, an HTTP request. */
  public Span startRoot(String kind, String participant, String label) {
    return start(tracer.spanBuilder(label).setNoParent(), SpanKind.SERVER, kind, participant, label);
  }

  /** A child of whatever span is current on this thread. */
  public Span startChild(String kind, SpanKind spanKind, String participant, String label) {
    return start(tracer.spanBuilder(label), spanKind, kind, participant, label);
  }

  private Span start(SpanBuilder builder, SpanKind spanKind, String kind, String participant, String label) {
    return builder
        .setSpanKind(spanKind)
        .setAttribute(KIND, kind)
        .setAttribute(PARTICIPANT, participant)
        .setAttribute(LABEL, label)
        .setAttribute(TICK_START, ticks.incrementAndGet())
        .startSpan();
  }

  public void end(Span span) {
    span.setAttribute(TICK_END, ticks.incrementAndGet());
    span.end();
  }

  public void event(Span span, String name, Attributes attributes) {
    span.addEvent(name, attributes.toBuilder().put(TICK, ticks.incrementAndGet()).build());
  }

  /** Nothing is recorded outside a diagram: Hibernate creating the schema, a scheduler, … */
  public boolean insideDiagram() {
    return Span.current().getSpanContext().isValid();
  }
}
