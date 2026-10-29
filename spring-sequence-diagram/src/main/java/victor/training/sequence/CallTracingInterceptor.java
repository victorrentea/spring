package victor.training.sequence;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.context.Scope;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

/**
 * One span per call that goes through a Spring proxy — the only calls {@code @Transactional} can see.
 * A call from a method to another one in the same class never reaches here, and that is the point:
 * no arrow in the diagram, no proxy, no transaction.
 * <p>
 * Ordered before the transaction interceptor, so the frame of a transaction opens <em>inside</em>
 * the arrow of the call that started it.
 */
class CallTracingInterceptor implements MethodInterceptor {
  private final SequenceRecorder recorder;
  private final List<String> basePackages;
  private final BooleanSupplier currentTransactionIsRollbackOnly;

  CallTracingInterceptor(SequenceRecorder recorder, List<String> basePackages, BooleanSupplier currentTransactionIsRollbackOnly) {
    this.recorder = recorder;
    this.basePackages = basePackages;
    this.currentTransactionIsRollbackOnly = currentTransactionIsRollbackOnly;
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    String participant = participant(invocation.getThis());
    String label = invocation.getMethod().getName() + "(" + summarize(invocation.getArguments()) + ")";
    Span span = recorder.startChild(SequenceRecorder.KIND_CALL, SpanKind.INTERNAL, participant, label);
    boolean rollbackOnlyBefore = currentTransactionIsRollbackOnly.getAsBoolean();
    try (Scope ignored = span.makeCurrent()) {
      return invocation.proceed();
    } catch (Throwable e) {
      span.setStatus(StatusCode.ERROR);
      span.setAttribute(SequenceRecorder.ERROR, e.getClass().getSimpleName());
      if (!rollbackOnlyBefore && currentTransactionIsRollbackOnly.getAsBoolean()) {
        // the exception crossed the proxy of a method that joined the caller's transaction
        recorder.event(span, SequenceRecorder.TX_ROLLBACK_ONLY, Attributes.empty());
      }
      throw e;
    } finally {
      recorder.end(span);
    }
  }

  private String participant(Object target) {
    if (target == null) return "?";
    if (Proxy.isProxyClass(target.getClass())) { // eg a Spring Data repository: name it after the interface you wrote
      for (Class<?> type : ClassUtils.getAllInterfacesForClass(target.getClass())) {
        if (basePackages.stream().anyMatch(p -> type.getName().startsWith(p + "."))) {
          return type.getSimpleName();
        }
      }
    }
    return ClassUtils.getUserClass(target).getSimpleName();
  }

  // never toString() an argument: on an entity it can trigger lazy loading, and the diagram would show its own SELECTs
  private static String summarize(Object[] args) {
    return Arrays.stream(args).map(CallTracingInterceptor::summarize).collect(Collectors.joining(", "));
  }

  private static String summarize(Object arg) {
    if (arg == null) return "null";
    if (arg instanceof CharSequence s) return "\"" + (s.length() > 20 ? s.subSequence(0, 20) + "…" : s) + "\"";
    if (arg instanceof Number || arg instanceof Boolean || arg instanceof Enum<?> || arg instanceof Character) return arg.toString();
    return ClassUtils.getUserClass(arg).getSimpleName();
  }
}
