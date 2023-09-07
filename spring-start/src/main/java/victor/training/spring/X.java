package victor.training.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.Y;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

//@Entity NO - hibernate manages it, not Spring

//@Configuration
//
@Component
//@Repository
//@Controller
//@RestController
//@Service // TODO what other annotation register this class as a bean
//@Mapper
public class X {
  // there is a SINGLE instance of X created by Spring
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
