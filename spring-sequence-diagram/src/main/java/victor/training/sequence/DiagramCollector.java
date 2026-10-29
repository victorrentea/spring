package victor.training.sequence;

import io.opentelemetry.context.Context;
import io.opentelemetry.sdk.trace.ReadWriteSpan;
import io.opentelemetry.sdk.trace.ReadableSpan;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.data.SpanData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Keeps the ended spans of each trace in memory; when the root span has ended and so has
 * every span under it — work on another thread can outlive its caller — the trace is
 * complete and becomes a diagram. Nothing leaves the JVM.
 */
class DiagramCollector implements SpanProcessor {
  private static final class Trace {
    final List<SpanData> spans = new ArrayList<>();
    int open;
    SpanData root;
  }

  private final Map<String, Trace> traces = new ConcurrentHashMap<>();
  private final PlantUmlGenerator generator;
  private final DiagramStore store;

  DiagramCollector(PlantUmlGenerator generator, DiagramStore store) {
    this.generator = generator;
    this.store = store;
  }

  @Override
  public void onStart(Context parentContext, ReadWriteSpan span) {
    Trace trace = traces.computeIfAbsent(span.getSpanContext().getTraceId(), k -> new Trace());
    synchronized (trace) {
      trace.open++;
    }
  }

  @Override
  public boolean isStartRequired() {
    return true;
  }

  @Override
  public void onEnd(ReadableSpan span) {
    SpanData data = span.toSpanData();
    Trace trace = traces.get(data.getTraceId());
    List<SpanData> spans;
    synchronized (trace) {
      trace.spans.add(data);
      trace.open--;
      if (!data.getParentSpanContext().isValid()) {
        trace.root = data;
      }
      if (trace.root == null || trace.open > 0) {
        return;
      }
      traces.remove(data.getTraceId());
      spans = trace.spans;
    }
    SpanData root = trace.root;
    if (spans.size() == 1 && SequenceRecorder.KIND_CALL.equals(root.getAttributes().get(SequenceRecorder.KIND))) {
      return; // a lone method call with nothing under it (a getter called by a framework) is not worth a diagram
    }
    String name = root.getAttributes().get(SequenceRecorder.LABEL);
    if (SequenceRecorder.KIND_CALL.equals(root.getAttributes().get(SequenceRecorder.KIND))) {
      name = root.getAttributes().get(SequenceRecorder.PARTICIPANT) + "." + name; // eg TransactionsApp.start()
    }
    store.save(name, generator.generate(name, spans));
  }

  @Override
  public boolean isEndRequired() {
    return true;
  }
}
