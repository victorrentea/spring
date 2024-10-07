package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

public class X {
  private final Y y;

  public X(Y y) { // constructor injection💖
    this.y = y;
  }

  public int logic() {
    return 1 + y.logic();
  }
}

