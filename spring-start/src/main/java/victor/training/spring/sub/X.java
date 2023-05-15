package victor.training.spring.sub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.spring.first.MailService;
import victor.training.spring.first.Y;


//@Repository // = persistenta DB Mongo Ora SQL .. File
//@Controller // endpointuri de HTTP din vremea .jsp .jsf vaadin velocity freemarker
//@RestController ⭐️// REST endpoints
//@Component ⭐️// nimic din cele de mai sus. ce rtamane . eg mapperi
//@Configuration ⭐️// are  @Bean definition in el

//@Retention(RetentionPolicy.RUNTIME)
//@Component
//@interface Mapper {
//}
//@Mapper

//@Entity// hibernate se ocupa de viata astora, nu sunt compo spring
// TODO what other annotation(s) registers this class as a bean ?

@Slf4j
@RequiredArgsConstructor // genereaza ctor pt campruile finale ❤️
@Service //  ⭐️ business rules
public class X {
//  @Autowired
//  private Y y; // #2 field injection (common in older projs)

  // #1 constructor injection ❤️
  private final Y y;

  // #3 method injection (rarely used)
//   @Autowired
//   public void init(Y y) {this.y2 = y;}

  public int logic() {
    return 1 + y.logic();
  }
}
