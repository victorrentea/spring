package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import victor.training.spring.first.pack.X;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class Y {
  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message;

  @Lazy
  private final X x;

//  public Y(String message, X x) {
//    this.message = message;
//    this.x = x;
//  }

//  public Y(@Lazy X x) {
//    this.x = x;
//  }

  @Value("${db.password}")
  private final String dbPass;

  @PostConstruct
  public void postConstruct() {
    System.out.println("Too Early sometimes: dbpass: " + dbPass);
  }

  @EventListener(ApplicationStartedEvent.class) // listening for an even thrown by spring during startup
  public void atAppStartup() {
    System.out.println("at startup dbpass: " + dbPass);
  }

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
}
