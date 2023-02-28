package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Configuration // not application logic but framework configuration; they can contain @Bean annotated methods
//
//@Component // What's left, eg. Mapper.
//@RestController // => REST API @GetMapping ...
@Service // => Domain Logic; is there any practical diff vs @Component. By default no.
// BUT we cand define an Aspect that intercept methods of any @Service
//@Repository // => DB access; Note: if using Spring Data Mongo you will just define an interface that extends a MongoRepository
//@Mapper // my own subtype of component
public class X {
  @Autowired
  private Y y; // field injection

  // (rarely used) method injection
  // public void setY(Y y) {this.y = y;}

  public int logic() {
    return 1 + y.logic();
  }
}
