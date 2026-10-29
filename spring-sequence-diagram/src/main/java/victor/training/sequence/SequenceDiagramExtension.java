package victor.training.sequence;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Scope;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

/**
 * Makes the test method body the root of a diagram — not @BeforeEach: the cleanup there would
 * crowd the picture.
 * <p>
 * Registered by {@link SequenceDiagram}, or for every test at once with JUnit's auto-detection
 * ({@code junit.jupiter.extensions.autodetection.enabled=true} in junit-platform.properties):
 * it stays out of the way of tests that start no Spring context, or one where it is disabled.
 */
public class SequenceDiagramExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
  private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(SequenceDiagramExtension.class);

  @Override
  public void beforeTestExecution(ExtensionContext context) {
    if (!isSpringTest(context.getRequiredTestClass())) {
      return;
    }
    SequenceRecorder recorder = SpringExtension.getApplicationContext(context)
        .getBeanProvider(SequenceRecorder.class).getIfAvailable();
    if (recorder == null) {
      return; // sequence.enabled=false
    }
    String name = context.getRequiredTestClass().getSimpleName() + "." + context.getRequiredTestMethod().getName();
    Span span = recorder.startRoot(SequenceRecorder.KIND_TEST, "Test", name);
    ExtensionContext.Store store = context.getStore(NAMESPACE);
    store.put("recorder", recorder);
    store.put("span", span);
    store.put("scope", span.makeCurrent());
  }

  @Override
  public void afterTestExecution(ExtensionContext context) {
    ExtensionContext.Store store = context.getStore(NAMESPACE);
    Scope scope = store.remove("scope", Scope.class);
    if (scope == null) {
      return;
    }
    scope.close();
    store.remove("recorder", SequenceRecorder.class).end(store.remove("span", Span.class));
  }

  private static boolean isSpringTest(Class<?> testClass) {
    for (Class<?> type = testClass; type != null; type = type.getEnclosingClass()) { // @Nested inherit from the outer class
      boolean spring = AnnotatedElementUtils.findMergedRepeatableAnnotations(type, ExtendWith.class).stream()
          .flatMap(extendWith -> Arrays.stream(extendWith.value()))
          .anyMatch(SpringExtension.class::equals);
      if (spring) return true;
    }
    return false;
  }
}
