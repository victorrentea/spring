package victor.training.spring.sub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.first.Y;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


//@Repository // = persistenta DB Mongo Ora SQL .. File
//@Controller // endpointuri de HTTP din vremea .jsp .jsf vaadin velocity freemarker
//@RestController ⭐️// REST endpoints
//@Component ⭐️// nimic din cele de mai sus. ce rtamane . eg mapperi
//@Configuration ⭐️// are  @Bean definition in el

//@Retention(RetentionPolicy.RUNTIME)
//@Component
//@interface Mapper {
//}
//@Mapper

//@Entity// hibernate se ocupa de viata astora, nu sunt compo spring
// TODO what other annotation(s) registers this class as a bean ?

@Service //  ⭐️ business rules
public class X {
  @Autowired
  private Y y; // #2 field injection (common in older projs)

  // #3 method injection (rarely used)
  // @Autowired public void init(Y y) {this.y2 = y;}

  public int logic() {
    return 1 + y.logic();
  }
}
