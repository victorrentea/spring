package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController // spring creates 1 instances (aka singleton)
// breaking news: there can be multiple HTTP requests concurrently

// AVOID KEEPING MUTALBE STATE in singletons.

public class BareSpringContainer {
  // fine in test
  private List<String> items = Collections.synchronizedList(new ArrayList<>()); // NEVER KEEP AS FIELD IN A SPRING-managed singleton
  // each day we were adding 10000 items, but forgetting to clear them at the end of the day
  // RISK: race bug if not synchronized access to mutable state

  private String currentUsername; // outrageous: current user for WHICH REQUEST

  @Scheduled(cron = "${clear.cron}")
  public void method() {
    items.clear();
  }
  @GetMapping("/asda")
  public void httpRequest() {
    items.add("aaa");
  }

  @Autowired
  private ApplicationContext applicationContext;

  private final X x; // WRONG! 1 single instance of X would ever be created for this injection point

  public BareSpringContainer(X x) {
    this.x = x;
  }

  @GetMapping
  public void met() {
//    X x = applicationContext.getBean("x"); // crash at runtime
//    X bean = applicationContext.getBean(X.class); // fetch my dependency
    // if X were a @Scope("prototype") you would get a new instance each time ✅

  }
}
