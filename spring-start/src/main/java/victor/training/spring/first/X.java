package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Component
@Retention(RUNTIME) // stops javac from removing it at compilation
@interface Adapter {

}
// TODO Define Beans
//@Service // by default (not in Picnic) Spring detects this class automatically and creates a bean
// Picnic didn't like that.
// They ask you to point to this class with an @Import annotation in a @Configuration class

//@Bean// YES, but not on the class


//@Controller // never use anymore, comes from the time .jsp/.jsf/.thymeleaf/VAADIN - not in Picnic
//@RestController // REST API returning JSON (@ResponseBody)

//@Service // busine$$ logic
//@Component // anything else (like a utility class)

@Adapter
//@Repository // DB access
public class X { // ONLY ONE INSTANCE IS CREATED BY SPRING = SINGLETON

//  @Autowired // field-based in @Test
//  private Y y; // requires spring to inject a reference to a bean of type Y for its
  // context (bucket of objects)

  private final Y y;
  public X(Y y) { // constructor based injection in production everywhere
    this.y = y;
  }
  // method-based injection for startup logic
  @Autowired
  public void met(Y y) { // imagine I wanted to send a kafka event only at the app start up
    System.out.println("I got a y: " + y);
  }

  public int logic() {
    System.out.println("X.logic");
    return 1 + y.logic();
  }
}

//class InATest {
//  X x = new X(y)
//}
