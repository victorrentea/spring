package victor.training.spring.first;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class X {
  private final YMe y;
  private final ApplicationContext applicationContext; // gateway to the Spring container
  // TERRIBLE MISTAKE in a spring-managed bean: mutable state
  // Spring beans are SINGLETONS by default, so they should not have
  // because they are shared between multiple threads
  // handling multiple requests in parallel

  // mutable data; and this data is bound to ONE request
//  private String currentUsername;
//  private String currentBasketId;
//  private String currentOrderId;

  // at picnic they take immutability very very serious
  // final *
  // ImmutableList
  // Immutables
  // records
  public X(YMe y, ApplicationContext applicationContext) { // constructor injection💖
    this.y = y;
    this.applicationContext = applicationContext;
  }

  @GetMapping("/logic")
  public int logic() {
    // DON'T DO THIS:
    Y2 y1 = applicationContext.getBean(Y2.class);// programatically retrieve a bean by type
    YMe y2 = applicationContext.getBean
        ("victor.training.spring.first.YMe", YMe.class); // or by name
    return 1 + y.logic();
  }
}

