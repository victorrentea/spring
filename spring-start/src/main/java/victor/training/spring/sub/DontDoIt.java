package victor.training.spring.sub;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;
import victor.training.spring.first.X;

@RequiredArgsConstructor
@RestController

public class DontDoIt {
  private final ApplicationContext applicationContext;
  private final Nui x; // GRESIT sa injectezi intru-un singleton un  "prototype"

  @GetMapping("candva")
  public void method() {
//    Nui x = applicationContext.getBean(Nui.class); // corect pt a fol un NOU prototype
// periculos ca-ti crapa la runtime, nu la startup
//    Nui x = applicationContext.getBean("nui",Nui.class); // !!! 💀
    x.logic();
  }
}
@Component
@Scope("prototype")
class Nui{
  // ai voie state aici in campuri
  //  private String currentUsername;

  public void logic() {
    System.out.println("Logica");
  }
}