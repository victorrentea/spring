package victor.training.spring.first.subp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import victor.training.spring.first.Y;

//@Entity NO - hibernate manages it, not Spring

//@Configuration // contain programatic @Bean definition
//
//@Repository // DB access
//@Controller // redirects, .jsp/.jsf server-side rendered HTML (not used anymore)
//@RestController // REST API
//@Service // contains BIZ LOGIC
@Component // infra/technicalities
//@Mapper // my own to express the semantic of a "mapper" component

public class XandY {
  // there is a SINGLE instance of X created by Spring
  // named "xandY" (small first letter)
  // ==>  needs to be STATE LESS (no request/user data in fields)
//  private String currentUser; // illegal
//  private String tenantId;
  @Autowired
  private Y y; // #2 field injection

  // #3 method injection (rarely used)
  // @Autowired public void init(Y y) {this.y2 = y;}

  public int logic() {
    return 1 + y.logic();
  }
}
