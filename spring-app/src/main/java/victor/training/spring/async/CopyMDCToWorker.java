package victor.training.spring.async;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

class CopyMDCToWorker implements TaskDecorator {
  @Override
  public Runnable decorate(Runnable runnable) {
    var parent = MDC.getCopyOfContextMap();
    return () -> {
      if (parent != null) {
        MDC.setContextMap(parent); // on the child thread
      }
      try {
        runnable.run();
      } finally {
        MDC.clear();
      }
    };
  }
}
