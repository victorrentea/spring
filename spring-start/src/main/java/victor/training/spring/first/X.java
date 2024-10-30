package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Retention(RetentionPolicy.RUNTIME)
//@Component
//@interface Manager {}

// sa se ocupe springu de asta = "new". cum?!
//@RestController // REST
//@Repository // DB
//@Controller // .jsp
//@Component ce nu se incadreaza in cele de mai sus
//@Manager

//@Configuration // in clase cu configuri de spring, fara pic de logica, de ob cu metode @Bean
// nu ruleaza cod per request

@Service // = biz logic
public class X {
  @Autowired
  private Y y;

  public int logic() {
    return 1 + y.logic();
  }
}
