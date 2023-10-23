package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Component // = everything else

//@Controller // = HTML generated on server with .jsp .jsf VAADIN Velocity Freemarker
//@RestController // REST API (JSON in out)

//@Service // = business logic

//@Repository // Data Access layer DB

//@Adapter

//@Configuration // @Bean
public class X {
  @Autowired
  private Y y; // #2 field injection

  // #3 method injection (rarely used)
  // @Autowired public void init(Y y) {this.y2 = y;}

  public int logic() {
    return 1 + y.logic();
  }
}
