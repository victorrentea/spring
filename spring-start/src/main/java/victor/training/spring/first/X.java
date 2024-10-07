package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

public class X {
  private final Y y;
  // TERRIBLE MISTAKE in a spring-managed bean: mutable state
  // Spring beans are SINGLETONS by default, so they should not have
  // because they are shared between multiple threads
  // handling multiple requests in parallel

  // mutable data; and this data is bound to ONE request
  private String currentUsername;
  private String currentBasketId;
  private String currentOrderId;

  public X(Y y) { // constructor injection💖
    this.y = y;
  }

  public int logic() {
    return 1 + y.logic();
  }
}

