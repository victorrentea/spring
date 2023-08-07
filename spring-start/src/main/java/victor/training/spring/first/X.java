package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service // TODO what other annotation register this class as a "bean"
public class X {
//  private final Y y;
//  // constructor based injection ❤️
//  public X(Y y) {
//    this.y = y;
//  }

//  @Autowired // method injection
//  public void init(Yyy y, Zzz z) {
//  }

  @Autowired
  ApplicationContext applicationContext;

  public int logic() {
    System.out.println("At runtime, 30 min later when ***that*** REST call happens");
    // risky: runtime failure if there is no Y bean defined in the app
    Y bean = applicationContext.getBean(Y.class); // manual bean fetching

    return 1 + bean.logic();
  }
}
