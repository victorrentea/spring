package victor.training.spring.first.pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import victor.training.spring.first.Y;


//@Controller // = HTML generated on server with .jsp .jsf VAADIN Velocity Freemarker
//@RestController // REST API (JSON in out)

//@Service // = business logic

//@Repository // Data Access layer DB

//@Adapter

//@Configuration // @Bean
@Component // = everything else
public class X {
  private final Y y;

  public X(Y y) {
    this.y = y;
  }

  @Autowired
  public void init(Y y, Y y2) {
    // only when you want to DO stuff with those dependencies, not just store them as fields
    System.out.println("Y: " + y);
  }

  public int logic() {
    return 1 + y.logic();
  }
}
