package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
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

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Bun cand startezi joburi spring din command line ");
  }

  @Autowired
  ApplicationEventPublisher eventPublisher;
  @EventListener(ApplicationStartedEvent.class)
  public void eventHandler() {
    System.out.println("Event la startup - pot accesa orice alt bean, totul merge deja");
    eventPublisher.publishEvent(new EventuMeu(12));
  }
}
record EventuMeu(int treaba) {}
//spring poate sa-ti care eventurile TALE de colo colo

@Component
class PesteMariSiTari {
  @EventListener
  public void method(EventuMeu eventuMeu) {
    System.out.println("Uite ce-a venit de la spring:" + eventuMeu);
  }
  @EventListener
  public void method2(EventuMeu eventuMeu) {
    System.out.println("Din nou:" + eventuMeu);
  }
}