package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

public class Y2 {
  private final int gate;

  Y2(@Value("${props.gate}") int gate) {
    this.gate = gate;
    System.out.println("gate in ctor " + gate);
  }

  @EventListener(ApplicationStartedEvent.class)
  public void spring_pleaseCallThisWhenTheAppIsReadyToReceiveRequests() {
    System.out.println("Gate @EventListener: " + gate);
  }
}
