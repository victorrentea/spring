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

//  @Autowired
//  private Y y; // field injection : bad becauase
  // 1) mutable field
  // 2) hard to test (bullshit because mockit can inject even private fields @InjectMocks)

  private final Y y;
  public X(Y y) { // constructor injection < recommended way
    this.y = y;
  }

  // (rarely used) 3 method injection
  private Y y2;
  @Autowired
  public void init(Y y) { // spring will call this method automatically when initializign hthe bean,
    // before it becomes ready to use
    this.y2 = y;
    System.out.println("I was given an instance of Y: " + y);
  }

  public int logic() {
    return 1 + y.logic();
  }
}
