package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// TODO what other annotation can I put here for Spring
//  to register this class as a bean ?
public class X {
  @Autowired
  private Y y; // #2 field injection (common in older projs)

  // #3 method injection (rarely used)
  // @Autowired public void init(Y y) {this.y2 = y;}

  public int logic() {
    return 1 + y.logic();
  }
}
