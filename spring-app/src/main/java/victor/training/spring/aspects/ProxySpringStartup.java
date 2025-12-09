package victor.training.spring.aspects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProxySpringStartup {
  @Autowired
  private SecondGrade secondGrade;

  @EventListener(ApplicationStartedEvent.class)
  public void runAtStartup() {
    System.out.println("Running Maths class...");
    secondGrade.mathClass();
  }
}