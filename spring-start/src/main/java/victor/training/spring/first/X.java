package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO Define Beans
@Service
public class X {
//  @Autowired // field injection
//  private Y y;

  private final Y y;
  public X(Y y) { // constructor injection
    this.y = y;
  }

  public int logic() {
    return 1 + y.logic();
  }
}
