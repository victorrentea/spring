package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Lazy // țeapă:
// ⚠️ poate crapa la runtime nu la startup
// ⚠️ foarte des degeaba daca A(lazy)->B(lazy)->C(!lazy eg RestController)
public class X {
  private final Y y;
  private final Z z;
  private final List<MailService> toate; // "filtre prin care treci pe rand"
  private final ApplicationContext applicationContext;


  public int logic() {
    return 1 + y.logic();
  }
  @GetMapping("lene")
  public void altEntypoint() {
    // ⚠️ poate beanul nici nu e definit -> runtime error
    Sloth intance = applicationContext.getBean(Sloth.class);
    intance.utila();
    Sloth.statica();
//    Environment environment = applicationContext.getEnvironment();
//    environment.getProperty()
  }
}
