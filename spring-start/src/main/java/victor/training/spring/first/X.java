package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class X {
  @Autowired
  private Y y;

  public int logic() {
    return 1 + y.logic();
  }
}
