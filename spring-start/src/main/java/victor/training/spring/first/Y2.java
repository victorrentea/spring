package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

public class Y2 {
  private final int gate;

  Y2(@Value("${props.gateOUPS:-1}") int gate, ApplicationContext applicationContext) {
    this.gate = gate;
    this.applicationContext = applicationContext;
    System.out.println("gate in ctor " + gate);
  }
  private final ApplicationContext applicationContext;

  @EventListener(ApplicationStartedEvent.class)
  public void spring_pleaseCallThisWhenTheAppIsReadyToReceiveRequests() {
    System.out.println("Gate @EventListener: " + gate);

//    System.out.println("From ctx:" + applicationContext.getEnvironment()
//        .getProperty("props.gateOUPS").toUpperCase());
  }
}

// can Immutables objects return null from a getter?
// NO. only empty Optional<>
