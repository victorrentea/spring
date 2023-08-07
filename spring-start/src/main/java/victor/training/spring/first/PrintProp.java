package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class PrintProp {
  private final String pass;

  PrintProp(@Value("${db.password}") String pass) {
    this.pass = pass;
  }
  // at app startup, print the value of the pass field
  @EventListener(ApplicationStartedEvent.class)
  public void method() {
    System.out.println("pass = " + pass);
  }
}