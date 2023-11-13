package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// TODO Defining Beans
public class X {
  @Autowired
  private Y y;
  // TODO Injection

  public int logic() {
    return 1 + y.logic();
  }
}
