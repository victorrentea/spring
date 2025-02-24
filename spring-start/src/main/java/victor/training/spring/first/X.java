package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

// any of these *should* make this class a "bean" managed by the Spring container
// not in Picnic though
@Service // business logic - requirements realization
//@Controller // dark ages of .jsp (server-generated HTML)
//@RestController // REST API endpoints
//@Repository // Database access
//@Component // none of the above: technical styff
public class X {
  @Autowired
  private Y y;

  public int logic() {
    return 1 + y.logic();
  }
}
