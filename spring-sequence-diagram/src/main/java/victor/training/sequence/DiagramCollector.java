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
 * Keeps the ended spans of each trace in memory; when the root span ends, the trace is
 * complete and becomes a diagram. Nothing leaves the JVM.
 */
class DiagramCollector implements SpanProcessor {
  private final Map<String, List<SpanData>> spansByTrace = new ConcurrentHashMap<>();
  private final PlantUmlGenerator generator;
  private final DiagramStore store;

  DiagramCollector(PlantUmlGenerator generator, DiagramStore store) {
    this.generator = generator;
    this.store = store;
  }

  @Override
  public void onEnd(ReadableSpan span) {
    SpanData data = span.toSpanData();
    List<SpanData> spans = spansByTrace.computeIfAbsent(data.getTraceId(), k -> new ArrayList<>());
    synchronized (spans) {
      spans.add(data);
    }
    if (data.getParentSpanContext().isValid()) {
      return;
    }
    spans = spansByTrace.remove(data.getTraceId());
    if (spans.size() == 1 && SequenceRecorder.KIND_CALL.equals(data.getAttributes().get(SequenceRecorder.KIND))) {
      return; // a lone method call with nothing under it (a getter called by a framework) is not worth a diagram
    }
    String name = data.getAttributes().get(SequenceRecorder.LABEL);
    if (SequenceRecorder.KIND_CALL.equals(data.getAttributes().get(SequenceRecorder.KIND))) {
      name = data.getAttributes().get(SequenceRecorder.PARTICIPANT) + "." + name; // eg TransactionsApp.start()
    }
    store.save(name, generator.generate(name, spans));
  }

  @Override
  public boolean isEndRequired() {
    return true;
  }

  @Override
  public void onStart(Context parentContext, ReadWriteSpan span) {
  }

  @Override
  public boolean isStartRequired() {
    return false;
  }
}
