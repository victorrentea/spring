package victor.training.spring.altu;

import org.springframework.beans.factory.annotation.Autowired;
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
  @Autowired
  private Y y;

  public int logic() {
    return 1 + y.logic();
  }
}
