package victor.training.spring.aspects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AtStartup {
  private final ApplicationContext context;
  @Autowired
  private ApplicationAvailability applicationAvailability;


  public AtStartup(ApplicationContext context) {
    this.context = context;
  }

  @EventListener(ApplicationStartedEvent.class)
  public void onStartup() {
//    applicationAvailability.State(ReadinessState.class)
    AvailabilityChangeEvent.publish(context, ReadinessState.REFUSING_TRAFFIC);
  }
}
