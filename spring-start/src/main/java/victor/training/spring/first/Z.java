package victor.training.spring.first;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Z {
  private final ApplicationContext applicationContext;

  public Z(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @GetMapping("boom")
  public void method() {
    // ❌ don't use this as a workaround the circular dependency between X and Y.
    // ❌ testing is harder: requires mocked ApplicationContext to return a mock T ❌❌❌❌
    // ❌ fails at runtime rather than at startup. later is more shameful/painful/constly...
    T t = applicationContext.getBean("t", T.class); // fetching. DON'T DO IT!❌
    t.logic();
//    System.out.println(applicationContext.getBean("x"));
  }
}

// what if I need multiple instances of T differently configured for eg NL,BE,DE warehouses?
class T {
  public void logic() {
  }
}

