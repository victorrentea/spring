package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

// any of these *should* make this class a "bean" managed by the Spring container
// not in Picnic though
//@Controller // dark ages of .jsp (server-generated HTML)
//@RestController // REST API endpoints
//@Repository // Database access
//@Component // none of the above: technical styff
@Service // business logic - requirements realization
public class X {
//  @Autowired // hey spring, give me a Y here < seen in @SpringBootTest tests
//  private Y y; // java frameworks don't give a sh*t about "private". they break in using "reflection"
// field injection (above) is descouraged
  // 1) because it makes the class harder to inject without the presence of this dependency injection framework
  // 2) it's not IMMUTABLE = religion in Picnic (records, Immutables) due to the heavy use of WebFlux

  private final Y y;
  public X(Y y) { // 💖
    this.y = y;
  }

  @Autowired // setter/method-injection = avoid
  public void whyyyyythwfeature(Y why) {
    why.logic();// call a method at init
  }

  public int logic() {
    return 1 + y.logic();
  }
  // above: dependency injection
  // ---
  // below: dependency resolution (avoid)

  @Autowired
  private ApplicationContext ctx;

  public void method() {
    ctx.getBean(Y.class).logic(); // crash when method() is called
    ctx.getBean("y", Y.class).logic(); // crash when method() is called
    // bad. too late.
  }
}

class Another {
  public void method() {
//    new X().y;// invisible
  }
}