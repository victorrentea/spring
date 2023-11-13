package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO Define Beans
@Service
public class X {
  // TODO Injection
  @Autowired
  private Y y;

  public int logic() {
    return 1 + y.logic();
  }
}
