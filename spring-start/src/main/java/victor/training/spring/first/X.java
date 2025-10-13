package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.spring.first.com.Z;

//Spring a gasit aceasta clasa prin "ComponentScan"ning: deschide toate claseel tale sa vada daca le-ai adnotat cu @Service, ...
// spring face 'new' 1 data = SINGLETON

//@Controller = HTML de pe server .jsp/.jsp/thymeleaf/Velocity/Vaadin
//@RestController = REST endpoints
//@Service = business logica (ce-o fi si ala?)
//@Repository = DB 💩 = SELECT SQL

//@Component = orice altceva ce nu e app logic

//@Configuration
@Service
public class X {
  // frameworkurile Java sunt imune la 'private'ul tau cu Reflection
  // cere lui spring sa injecteze aici acea instanta unica de Y
  // field-injection
//  @Autowired // decat in integration testing
  private final Y y;

  public X(Y y) { // ❤️ constructor injection
    this.y = y;
  }

  @Autowired // IoC: tu ceri, Springul iti da!
  public void laStartupDamiUn(Z z) {
    System.out.println("Fac ceva cu z: " + z);
  }


  public int logic() {
    //    Y y = new Y(); // RAU
    // - in testare cum puii ma-sii strecor eu aici un Mock???!😡
    // - performanta? daca fac asta in 1000x de locuri
    // - sharing state: Hibernate->JDBC Connection pool maxsize=10->SQL
    // - daca as avea mai multe impl de YInteraface
    // - daca as vrea sa intereceptez apelul facut mai jos catre logic() pt a face lucruri magice in plus
    return 1 + y.logic();
  }
}
