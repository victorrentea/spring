package victor.training.spring.first.alt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.Y;

// Spring promoveaza separatia intre layere tehnice
// la startup spring scaneaza dupa astfel de clase toate pachetele
  // de sub pachetul in care ai pus @SpringBootApplication
  // = ComponentScan
//@RestController // expune API REST: @GetMapping, @PostMapping
//@Controller //X  generezi HTML de pe server: .jsp✅ .jsf VAADIN✅ Velocity Freemarker
//@Service // business logic
//@Repository // clasa persista date intr-o DB (SQL, Mongo,...)

@Component // pe orice altceva (chestii tehnice)
// TODO Defining Beans (whose life is managed by Spring Container)
public class X {
  @Autowired
  private Y y;
  // TODO Injection

  public int logic() {
    return 1 + y.logic();
  }
}
