package victor.training.spring.sub;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.X;

@RequiredArgsConstructor
@RestController
public class DontDoIt {
  private final ApplicationContext applicationContext;
  private final Nui x; // fails at startup

  @GetMapping("candva")
  public void method() {
//    Nui x = applicationContext.getBean(Nui.class); // periculos ca-ti crapa la runtime, nu la startup
//    Nui x = applicationContext.getBean("nui",Nui.class); // !!! 💀
    x.logic();
  }
}
@Component
class Nui{
  public void logic() {
    System.out.println("Logica");
  }
}