package victor.training.sequence;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.listener.MethodExecutionListener;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.proxy.ParameterSetOperation;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Sees what really reaches the database through the JDBC driver: every statement with its
 * parameters, and the connection-level calls that are the transaction —
 * {@code setAutoCommit(false)}, {@code commit()}, {@code rollback()} — plus taking a connection
 * from the pool and giving it back.
 * <p>
 * It sits between your code and the pool, so it works with any driver and any database.
 */
class JdbcTracingListener implements QueryExecutionListener, MethodExecutionListener {
  private final SequenceRecorder recorder;
  private final boolean showConnections;
  private final String database;

  /** @param database the lifeline: one per DataSource, so two databases draw two */
  JdbcTracingListener(SequenceRecorder recorder, boolean showConnections, String database) {
    this.recorder = recorder;
    this.showConnections = showConnections;
    this.database = database;
  }

  @Override
  public void afterQuery(ExecutionInfo execution, List<QueryInfo> queries) {
    if (!recorder.insideDiagram()) return;
    for (QueryInfo query : queries) {
      String sql = query.getQuery().replaceAll("\\s+", " ").trim();
      String operation = sql.split(" ", 2)[0].toUpperCase(Locale.ROOT);
      String statement = query.getParametersList().isEmpty() ? sql : inline(sql, query.getParametersList().get(0));
      if (execution.isBatch() && query.getParametersList().size() > 1) {
        statement += "  ×" + query.getParametersList().size() + " (batch)";
      }
      Span span = recorder.startChild(SequenceRecorder.KIND_DB, SpanKind.CLIENT, database, statement);
      span.setAttribute(SequenceRecorder.DB_OPERATION, operation);
      span.setAttribute(SequenceRecorder.DB_STATEMENT, statement);
      if (!execution.isSuccess()) {
        span.setStatus(StatusCode.ERROR);
        span.setAttribute(SequenceRecorder.ERROR, execution.getThrowable().getClass().getSimpleName());
      }
      recorder.end(span);
    }
  }

  @Override
  public void afterMethod(MethodExecutionContext context) {
    if (!recorder.insideDiagram()) return;
    String method = context.getMethod().getName();
    Object[] args = context.getMethodArgs();
    String operation = null;
    String label = null;
    if (context.getTarget() instanceof DataSource && method.equals("getConnection")) {
      operation = "ACQUIRE";
      label = "getConnection() from pool";
    } else if (context.getTarget() instanceof Connection) {
      switch (method) {
        case "setAutoCommit" -> {
          if (Boolean.FALSE.equals(args[0])) {
            operation = "BEGIN";
            label = "setAutoCommit(false)";
          }
        }
        case "commit" -> operation = label = "COMMIT";
        case "rollback" -> operation = label = args == null || args.length == 0 ? "ROLLBACK" : "ROLLBACK TO SAVEPOINT";
        case "setSavepoint" -> operation = label = "SAVEPOINT";
        case "close" -> {
          operation = "RELEASE";
          label = "close() = back to pool";
        }
      }
    }
    if (operation == null || !showConnections && (operation.equals("ACQUIRE") || operation.equals("RELEASE"))) {
      return;
    }
    Span span = recorder.startChild(SequenceRecorder.KIND_DB, SpanKind.CLIENT, database, label);
    span.setAttribute(SequenceRecorder.DB_OPERATION, operation);
    if (context.getThrown() != null) {
      span.setStatus(StatusCode.ERROR);
      span.setAttribute(SequenceRecorder.ERROR, context.getThrown().getClass().getSimpleName());
    }
    recorder.end(span);
  }

  // the SQL as you would paste it in a console: every ? replaced by its value
  private static String inline(String sql, List<ParameterSetOperation> parameters) {
    List<Object> values = parameters.stream()
        .filter(p -> p.getArgs().length >= 2 && p.getArgs()[0] instanceof Integer)
        .sorted(Comparator.comparing(p -> (Integer) p.getArgs()[0]))
        .map(p -> p.getMethod().getName().equals("setNull") ? null : p.getArgs()[1])
        .toList();
    StringBuilder out = new StringBuilder();
    int next = 0;
    for (char c : sql.toCharArray()) {
      if (c == '?' && next < values.size()) {
        Object value = values.get(next++);
        out.append(value instanceof CharSequence || value instanceof java.util.Date || value instanceof java.time.temporal.Temporal
            ? "'" + value + "'" : String.valueOf(value));
      } else {
        out.append(c);
      }
    }
    return out.toString();
  }

  @Override
  public void beforeQuery(ExecutionInfo execution, List<QueryInfo> queries) {
  }

  @Override
  public void beforeMethod(MethodExecutionContext context) {
  }
}
