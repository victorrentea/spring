package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO Define Beans
@Service
public class X { //
//  @Autowired // field-based in @Test
//  private Y y; // requires spring to inject a reference to a bean of type Y for its
  // context (bucket of objects)

  private final Y y;
  public X(Y y) { // constructor based injection in production everywhere
    this.y = y;
  }
  // method-based injection for startup logic
  @Autowired
  public void met(Y y) { // imagine I wanted to send a kafka event only at the app start up
    System.out.println("I got a y: " + y);
  }

  public int logic() {
    System.out.println("X.logic");
    return 1 + y.logic();
  }
}

//class InATest {
//  X x = new X(y)
//}
