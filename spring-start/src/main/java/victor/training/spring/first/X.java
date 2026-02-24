package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

//@Controller // is for server-side generated HTML: .jsp, .jsf, vaadin, velocity
//@RestController // REST API calls

@Service // business logic 🧠🧠

//@Repository // DB access

//all above are
// @Component // when none of te above applies < marks it for @ComponentScan that Picnic doesn't use.

public class X {
  @Autowired
  private Y y;
  @Autowired
  private Z z;

  public int logic() {
    return 1 + y.logic();
  }
}
