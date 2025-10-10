package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Component
@Retention(RetentionPolicy.RUNTIME) // javac preserves it
@interface Adapter {

}
// hey spring, make this class a bean in my applicationContext

//@RestController// REST API
@Service // business logic = impl most of the application logic / tech agnostic (not HTTP, not SQL, not Kafka)
//@Adapter
//@Repository // talk to DB

//@Component // anything else, non-app logic. spring, infra, security stuff

// NOT for:
// "data models" : DTO/json, jooq record, domain model POJO <- not managed by Spring
// we do new, pass them around.
// are NOT singletons
// are NOT managed by Spring nor injected by Spring
public class X {
  private final Y y;
  private final Z z;
//  private final ApplicationContext applicationContext;

  public X(Y y, Z z, ApplicationContext applicationContext) { // hey spring, inject here an instance of the bean Y
    this.y = y;
    this.z = z;
//    this.applicationContext = applicationContext;
  }

//  @Autowired // don't
//  public void setY(Y y) {
//    y.callThisAtStart()
//  }
  public int logic() {
//    MailService bean = applicationContext.getBean(MailService.class);// risky, avoid!
    // only fails when logic() is called if there's no such bean
    return 1 + y.logic();
  }
}
