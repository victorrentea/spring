package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(Y.class)
public class X2 {
  @Autowired // tells spring to inject a Y instance here
  private Y y;

  public int logic() {
    return 1 + y.logic();
  }
}

