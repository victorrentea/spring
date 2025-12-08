package subp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.spring.first.Y;
// clasa devine bean = obiect instantiat, config, manipulat de Spring, daca:

//@Controller = returneaza HTML (cu Thymeleaf,JSP...)
//@RestController // = REST API care intoarce JSON
//@Repository = interactioneaza cu baza de date, si atat!
//@Component = orice nu se incadreaza la cele de mai sus (ce-a ramas)
//@CamelEndpoint // pot #sieu
@Service //= logica aplicatiei de business
@Slf4j
@RequiredArgsConstructor // genereaza cod la javac
public class X {
// Dependency Injection = la startup, Spring cauta un bean de tip Y si il injecteaza aici
//  @Autowired // #1 field injection folosita doar in teste
//  private Y y;
  private final Y y; // #2 constructor injection (recomandat + Lombok❤️)
//  public X(Y y) {
//    this.y = y;
//  }
  public int logic() {
    log.info("Ceva");
    return 1 + y.logic();
  }
  @Autowired // #3 method injection ❌ n-o folosim
    // in afara cazului cand mai vrei sa FOLOSESTI y
  private void inject(Y y) {
    log.info("Am primit " + y);
//    this.y = y;
  }
}
