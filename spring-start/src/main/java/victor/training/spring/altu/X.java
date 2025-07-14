package victor.training.spring.altu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import victor.training.spring.first.Y;

//@RestController
//class C {}
//@Controller// pe vremuri se scria HTML in server .jsp, .jsf, Vaadin
//@RestController // @GetMapping api REST
//@Service // logica de business
//@Repository // acces la DB

//@Component // = nimic de mai sus; respo tehnice: filtru http, interceptor, aspect

//@Configuration // defineste @Bean
public class X {
  // un bean = o instanta managed by Spring din clasa ta

  private final Y y;
  private final ApplicationContext applicationContext;

  public X(Y y, ApplicationContext applicationContext) {
    this.y = y; // # DI = bine
    this.applicationContext = applicationContext;
  }

  public int logic() {
//    var y = applicationContext.getBean(Y.class); // # rau dep fetching:
    // 1 pot depinde de orice
    // 2 greu de testat -> @Mock 🤢
    // 3 crapa prea tarziu: la runtime, nu la init
    return 1 + y.logic();
  }
}
