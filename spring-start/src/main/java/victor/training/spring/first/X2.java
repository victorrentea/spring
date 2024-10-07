package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

public class X2 {
  @Autowired // field injection 😡😡 not good; tells spring to inject a Y instance here
  private Y y; // framework will set this field for you using reflection

  // @Bean
  // @GetMapping
  // @Scheduled
  // @EventListener
  @Autowired // method injection 😡(not traditional)
  public void method(Y y) {
    System.out.println("Got "+ y + " called by Spring" +
                       " that acts here as an IoC (Inversion of Control)" +
                       " container");
    // the hollywood principle: "Don't call us, we'll call you"

    // RULE: injection happens at startup time,
    // before the bean gets any calls from eg. Rest, Kafka...
//    throw new RuntimeException("Proof:ouuuu crashes the startup of the app");
  }

  public int logic() {
    return 1 + y.logic();
  }
}

