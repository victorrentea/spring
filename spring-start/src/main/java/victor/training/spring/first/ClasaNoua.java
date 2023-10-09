package victor.training.spring.first;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ClasaNoua implements CommandLineRunner {
  public ClasaNoua() {
    System.out.println("new()");
  }
  @PostConstruct
  public void init() {
    System.out.println("Hello Spring!");
  }

  @EventListener(ApplicationStartedEvent.class)
  public void eventHandler() {
    System.out.println("Event la startup - pot accesa orice alt bean, totul merge deja");
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Bun cand startezi joburi spring din command line ");
  }
}
