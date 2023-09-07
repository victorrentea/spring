package victor.training.spring.first.subp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.Y;

//@Entity NO - hibernate manages it, not Spring

//@Configuration // contain programatic @Bean definition
//
//@Repository // DB access
//@Controller // redirects, .jsp/.jsf server-side rendered HTML (not used anymore)
//@RestController // REST API
//@Service // contains BIZ LOGIC
//@Mapper // my own to express the semantic of a "mapper" component

@Slf4j
@RequiredArgsConstructor // pragmatic ctor injection
@Service // infra/technicalities
public class XandY {
  // there is a SINGLE instance of X created by Spring
  // named "xandY" (small first letter)
  // ==>  needs to be STATE LESS (no request/user data in fields)
//  private String currentUser; // illegal
//  private String tenantId;

  //  @Autowired
//  private Y y; // #2 field injection
  private final Y y;
//  public XandY(Y y) { // #1 ❤️constructor
//    this.y = y;
//  }

  // #3 method injection (rarely used)
//  @Autowired
//  public void init(Y y) {
//    // only use if you DO something with y, not just store it in a field
//    this.y2 = y;
//  }

  public int logic() {
    return 1 + y.logic();
  }
}
