package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// TODO print DB pass in console at startup
@Component
public class Config {
  @Value("${db.password:defaultVal}")
  private String pass;

  @EventListener(ApplicationStartedEvent.class)
  public void method() {
    System.out.println("Parola este: " + pass);
  }
}
