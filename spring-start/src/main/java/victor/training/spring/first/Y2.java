package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

public class Y2 {
  Y2() { // in order to write in the gate field,
    // spring first has to 'new' this class
    System.out.println("Gate constructor: " + gate); // too early
  }

  @Value("${props.gate}")
  private Integer gate;

  @EventListener(ApplicationStartedEvent.class)
  public void spring_pleaseCallThisWhenTheAppIsReadyToReceiveRequests() {
    System.out.println("Gate @EventListener: " + gate);
  }
}
