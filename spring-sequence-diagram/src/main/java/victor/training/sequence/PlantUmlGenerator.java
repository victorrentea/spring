package victor.training.sequence;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.sdk.trace.data.EventData;
import io.opentelemetry.sdk.trace.data.SpanData;

import java.util.*;

import static victor.training.sequence.SequenceRecorder.*;

/**
 * Turns the spans of one trace into a PlantUML sequence diagram:
 * a participant per bean, an arrow per call through a proxy, an arrow to DB per JDBC call,
 * and a coloured frame around everything that ran inside the same physical transaction.
 * <p>
 * A suspended transaction (REQUIRES_NEW, NOT_SUPPORTED) closes its frame, and reopens it as
 * "(resumed)" — nested frames would wrongly suggest one transaction contains the other.
 * For the same reason, work that ran on another thread is drawn in a 🧵 frame of its own, outside
 * every transaction of the thread that handed it over.
 */
public class PlantUmlGenerator {
  // header, body, text — one hue per transaction, so a resumed frame is recognisably the same one
  private static final String[][] PALETTE = {
      {"#A5D6A7", "#F1F8E9", "#1B5E20"},
      {"#90CAF9", "#E3F2FD", "#0D47A1"},
      {"#FFCC80", "#FFF3E0", "#E65100"},
      {"#CE93D8", "#F3E5F5", "#4A148C"},
      {"#80CBC4", "#E0F2F1", "#004D40"},
  };

  /** @param thread only on the two steps opening and closing the work of another thread */
  private record Step(long tick, SpanData span, boolean start, EventData event, String thread) {
    Step(long tick, SpanData span, boolean start, EventData event) {
      this(tick, span, start, event, null);
    }
  }

  private record Tx(int number, String name, String flags, boolean committed, String suspends) {
  }

  private static final class Frame {
    final long txId;
    final String participant;
    int messages;

    Frame(long txId, String participant) {
      this.txId = txId;
      this.participant = participant;
    }
  }

  private final int maxLabelLength;

  public PlantUmlGenerator(int maxLabelLength) {
    this.maxLabelLength = maxLabelLength;
  }

  public String generate(String title, List<SpanData> spans) {
    Map<String, SpanData> byId = new HashMap<>();
    spans.forEach(s -> byId.put(s.getSpanId(), s));
    List<Step> steps = drawingOrder(timeline(spans), byId);
    Map<Long, Tx> txs = transactions(steps);

    Set<String> participants = new LinkedHashSet<>();
    Set<String> actors = new HashSet<>();
    Set<String> databases = new LinkedHashSet<>(); // drawn right-most, in order of first use
    List<String> body = new ArrayList<>();
    Deque<Frame> frames = new ArrayDeque<>();
    Set<String> answered = new HashSet<>();
    int framesOutsideThread = 0;

    for (Step step : steps) {
      SpanData span = step.span();
      Attributes a = span.getAttributes();
      String kind = a.get(KIND);
      if (step.thread() != null) {
        if (step.start()) {
          body.add("group#BDBDBD #FAFAFA <color:#424242><b>🧵 " + escape(step.thread()) + "</b> · another thread: in none of the transactions above");
          framesOutsideThread = frames.size();
        } else {
          while (frames.size() > framesOutsideThread) closeFrame(frames, body);
          body.add("end");
        }
        continue;
      }
      if (step.event() != null) {
        frameEvent(step.event(), participantOf(span), txs, frames, body);
        continue;
      }
      if (KIND_TEST.equals(kind) || KIND_HTTP.equals(kind)) {
        participants.add(a.get(PARTICIPANT));
        actors.add(a.get(PARTICIPANT));
        continue;
      }
      String caller = callerOf(span, byId);
      if (KIND_DB.equals(kind) && byId.containsKey(span.getParentSpanId())
          && KIND_HTTP.equals(byId.get(span.getParentSpanId()).getAttributes().get(KIND))) {
        caller = "Spring"; // framework code around your controller, eg open-session-in-view closing the connection
      }
      String callee = a.get(PARTICIPANT);
      SpanData parent = byId.get(span.getParentSpanId());
      boolean parentIsHttp = parent != null && KIND_HTTP.equals(parent.getAttributes().get(KIND));
      if (KIND_DB.equals(kind)) {
        if (step.start()) {
          participants.add(caller);
          databases.add(callee);
          body.add(arrow(caller, callee, dbLabel(a)));
          countMessage(frames);
        }
      } else if (step.start()) {
        participants.add(callee);
        String label = escape(a.get(LABEL));
        if (parentIsHttp && answered.add(parent.getSpanId() + "->")) {
          label = "<b>" + escape(parent.getAttributes().get(LABEL)) + "</b>\\n" + label;
        }
        body.add(arrow(caller, callee, label));
        body.add("activate " + quote(callee));
        countMessage(frames);
      } else {
        String error = a.get(ERROR);
        if (error != null) {
          body.add(returnArrow(callee, caller, "<color:#C62828>⚡ " + error));
        } else if (parentIsHttp && answered.add(parent.getSpanId() + "<-")) {
          Long status = parent.getAttributes().get(HTTP_STATUS);
          body.add(returnArrow(callee, caller, status == null ? "" : String.valueOf(status)));
        }
        body.add("deactivate " + quote(callee));
      }
    }
    while (!frames.isEmpty()) {
      closeFrame(frames, body);
    }

    List<String> out = new ArrayList<>();
    out.add("@startuml");
    out.add("' generated from the OpenTelemetry spans of one run — see " + getClass().getName());
    out.add("hide footbox");
    out.add("skinparam sequenceGroupBorderThickness 2");
    out.add("skinparam sequenceGroupBorderColor #555555");
    out.add("skinparam maxMessageSize 400");
    out.add("title " + escape(title));
    for (String p : participants) {
      out.add((actors.contains(p) ? "actor " : "participant ") + quote(p));
    }
    for (String database : databases) {
      out.add("database " + quote(database));
    }
    out.addAll(body);
    out.add("@enduml");
    return String.join("\n", out) + "\n";
  }

  private static List<Step> timeline(List<SpanData> spans) {
    List<Step> steps = new ArrayList<>();
    for (SpanData span : spans) {
      Attributes a = span.getAttributes();
      steps.add(new Step(Objects.requireNonNullElse(a.get(TICK_START), 0L), span, true, null));
      steps.add(new Step(Objects.requireNonNullElse(a.get(TICK_END), Long.MAX_VALUE), span, false, null));
      for (EventData event : span.getEvents()) {
        steps.add(new Step(Objects.requireNonNullElse(event.getAttributes().get(TICK), 0L), span, false, event));
      }
    }
    steps.sort(Comparator.comparingLong(Step::tick));
    return steps;
  }

  // the work of another thread (CompletableFuture.runAsync, a parallel stream) is drawn in one piece,
  // at the first moment after it started when its diagram's thread had no transaction open
  private static List<Step> drawingOrder(List<Step> steps, Map<String, SpanData> byId) {
    List<Step> diagramThread = new ArrayList<>();
    Map<String, List<Step>> otherThreads = new LinkedHashMap<>(); // by the first span of that work, in order of start
    for (Step step : steps) {
      SpanData first = firstOnOtherThread(step.span(), byId);
      if (first == null) {
        diagramThread.add(step);
      } else {
        otherThreads.computeIfAbsent(first.getSpanId(), k -> new ArrayList<>()).add(step);
      }
    }
    if (otherThreads.isEmpty()) {
      return steps;
    }
    Deque<List<Step>> pending = new ArrayDeque<>(otherThreads.values());
    List<Step> out = new ArrayList<>();
    int openTransactions = 0;
    for (Step step : diagramThread) {
      while (openTransactions == 0 && !pending.isEmpty() && pending.peek().get(0).tick() < step.tick()) {
        addOtherThread(pending.pop(), out);
      }
      out.add(step);
      if (step.event() != null) {
        openTransactions = Math.max(0, openTransactions + switch (step.event().getName()) {
          case TX_BEGIN, TX_RESUME -> 1;
          case TX_SUSPEND, TX_COMMIT, TX_ROLLBACK -> -1;
          default -> 0;
        });
      }
    }
    pending.forEach(work -> addOtherThread(work, out));
    return out;
  }

  private static void addOtherThread(List<Step> work, List<Step> out) {
    SpanData first = work.get(0).span();
    String thread = first.getAttributes().get(THREAD);
    out.add(new Step(work.get(0).tick(), first, true, null, thread));
    out.addAll(work);
    out.add(new Step(work.get(work.size() - 1).tick(), first, false, null, thread));
  }

  private static SpanData firstOnOtherThread(SpanData span, Map<String, SpanData> byId) {
    for (SpanData s = span; s != null; s = byId.get(s.getParentSpanId())) {
      if (s.getAttributes().get(THREAD) != null) return s;
    }
    return null;
  }

  // first pass: number the transactions in order of appearance and learn how each one ended
  private static Map<Long, Tx> transactions(List<Step> steps) {
    Map<Long, Tx> txs = new LinkedHashMap<>();
    Set<Long> suspended = new LinkedHashSet<>();
    for (Step step : steps) {
      if (step.event() == null) continue;
      Attributes a = step.event().getAttributes();
      Long id = a.get(TX_ID);
      if (id == null) continue;
      switch (step.event().getName()) {
        case TX_BEGIN -> {
          String suspends = suspended.stream().map(s -> txs.containsKey(s) ? "tx#" + txs.get(s).number() : "?")
              .reduce((x, y) -> x + ", " + y).orElse(null);
          txs.put(id, new Tx(txs.size() + 1, a.get(TX_NAME), a.get(TX_FLAGS), false, suspends));
        }
        case TX_SUSPEND -> suspended.add(id);
        case TX_RESUME -> suspended.remove(id);
        case TX_COMMIT -> txs.computeIfPresent(id, (k, t) -> new Tx(t.number(), t.name(), t.flags(), true, t.suspends()));
        default -> {
        }
      }
    }
    return txs;
  }

  private static void frameEvent(EventData event, String participant, Map<Long, Tx> txs, Deque<Frame> frames, List<String> body) {
    Long id = event.getAttributes().get(TX_ID);
    Tx tx = id == null ? null : txs.get(id);
    switch (event.getName()) {
      case TX_BEGIN -> openFrame(id, tx, participant, false, frames, body);
      case TX_RESUME -> {
        if (tx != null) openFrame(id, tx, participant, true, frames, body);
      }
      case TX_SUSPEND, TX_COMMIT, TX_ROLLBACK -> {
        if (frames.stream().anyMatch(f -> f.txId == id)) {
          while (frames.peek().txId != id) closeFrame(frames, body);
          closeFrame(frames, body);
        }
      }
      case TX_ROLLBACK_ONLY -> {
        String which = frames.isEmpty() ? "the current transaction" : "tx#" + txs.get(frames.peek().txId).number();
        body.add("hnote over " + quote(participant) + " #FFCDD2: ☠ " + which + " marked <b>rollback-only</b>");
        countMessage(frames);
      }
      default -> {
      }
    }
  }

  private static void openFrame(long id, Tx tx, String participant, boolean resumed, Deque<Frame> frames, List<String> body) {
    String[] colors = PALETTE[(tx.number() - 1) % PALETTE.length];
    StringBuilder header = new StringBuilder("<color:" + colors[2] + "><b>tx#" + tx.number() + "</b>");
    if (resumed) {
      header.append(" (resumed)");
    } else {
      header.append(" · @Transactional ").append(escape(tx.name()));
      if (tx.flags() != null && !tx.flags().isEmpty()) header.append(" · ").append(tx.flags());
      if (tx.suspends() != null) header.append(" · ").append(tx.suspends()).append(" suspended");
      header.append(tx.committed() ? " — ✅ COMMIT" : " — <color:#C62828>❌ ROLLBACK</color>");
    }
    body.add("group" + colors[0] + " " + colors[1] + " " + header);
    frames.push(new Frame(id, participant));
  }

  private static void closeFrame(Deque<Frame> frames, List<String> body) {
    Frame frame = frames.pop();
    if (frame.messages == 0) { // PlantUML draws an empty group as a sliver; say why it is empty
      body.add("hnote over " + quote(frame.participant) + ": (no calls)");
    }
    body.add("end");
  }

  private static void countMessage(Deque<Frame> frames) {
    if (!frames.isEmpty()) frames.peek().messages++;
  }

  private String dbLabel(Attributes a) {
    String operation = Objects.requireNonNullElse(a.get(DB_OPERATION), "");
    String label = escape(truncate(a.get(LABEL)));
    String styled = switch (operation) {
      case "COMMIT" -> "<color:#2E7D32><b>COMMIT</b>";
      case "ROLLBACK", "ROLLBACK TO SAVEPOINT" -> "<color:#C62828><b>" + label + "</b>";
      case "BEGIN", "SAVEPOINT" -> "<color:#555555><b>" + label + "</b>";
      case "ACQUIRE", "RELEASE" -> "<color:#888888><size:11>🔌 " + label + "</size>";
      case "INSERT", "UPDATE", "DELETE", "MERGE" -> "<color:#B71C1C><b>" + operation + "</b>" + label.substring(operation.length());
      default -> label;
    };
    String error = a.get(ERROR);
    return error == null ? styled : styled + "\\n<color:#C62828>⚡ " + error;
  }

  private static String participantOf(SpanData span) {
    return span.getAttributes().get(PARTICIPANT);
  }

  private static String callerOf(SpanData span, Map<String, SpanData> byId) {
    if (span.getAttributes().get(THREAD) != null) {
      return "["; // whoever submitted the task, it is not the span that happened to be open on the other thread
    }
    SpanData parent = byId.get(span.getParentSpanId());
    while (parent != null && KIND_DB.equals(parent.getAttributes().get(KIND))) {
      parent = byId.get(parent.getParentSpanId());
    }
    return parent == null ? "[" : participantOf(parent);
  }

  private static String arrow(String from, String to, String label) {
    return (from.equals("[") ? "[->" : quote(from) + " ->") + " " + quote(to) + ": " + label;
  }

  private static String returnArrow(String from, String to, String label) {
    return to.equals("[") ? "[<-- " + quote(from) + ": " + label : quote(from) + " --> " + quote(to) + ": " + label;
  }

  private static String quote(String participant) {
    return "\"" + participant + "\"";
  }

  private String truncate(String s) {
    return s == null ? "" : s.length() <= maxLabelLength ? s : s.substring(0, maxLabelLength) + "…";
  }

  // PlantUML reads \n as a line break and creole markup in doubled characters: keep SQL literal
  static String escape(String s) {
    if (s == null) return "";
    String out = s.replace("\\", "\\\\").replace("~", "~~");
    for (String markup : List.of("**", "//", "\"\"", "__", "--", "==")) {
      out = out.replace(markup, "~" + markup);
    }
    return out;
  }
}
