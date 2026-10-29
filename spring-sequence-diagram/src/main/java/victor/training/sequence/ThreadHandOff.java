package victor.training.sequence;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.ContextKey;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.Callable;

/**
 * Carries the diagram over to the thread that runs a task handed to an executor bean:
 * an {@code @Async} method, {@code CompletableFuture.supplyAsync(task, executor)}, {@code executor.submit(task)}.
 * Wrapped around every {@code Executor} bean by {@link SequenceAutoConfiguration}.
 * <p>
 * The first span recorded on that thread is marked with its name, so it is drawn in a 🧵 frame of
 * its own — see {@link PlantUmlGenerator}.
 */
class ThreadHandOff implements MethodInterceptor {
  private static final ContextKey<HandOff> HAND_OFF = ContextKey.named("seq.hand-off");

  /** @param from the span open on the thread that submitted the task */
  private record HandOff(Span from, Thread thread) {
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Object[] args = invocation.getArguments(); // the very array the target is called with
    for (int i = 0; i < args.length; i++) {
      if (args[i] instanceof Runnable task) {
        args[i] = handOff().wrap(task);
      } else if (args[i] instanceof Callable<?> task) {
        args[i] = handOff().wrap(task);
      }
    }
    return invocation.proceed();
  }

  // outside a diagram: the current context, with nothing to carry
  private static Context handOff() {
    Span from = Span.current();
    if (!from.getSpanContext().isValid()) {
      return Context.current();
    }
    return Context.current().with(HAND_OFF, new HandOff(from, Thread.currentThread()));
  }

  /** Whether a span started now is the first one of a task that came from another thread. */
  static boolean firstOnAnotherThread() {
    HandOff handOff = Context.current().get(HAND_OFF);
    return handOff != null && handOff.from() == Span.current() && handOff.thread() != Thread.currentThread();
  }
}
