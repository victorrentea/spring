package victor.training.spring.alt;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import victor.training.spring.first.Props;
import victor.training.spring.pachet.pachet.X;

@Configuration
public class Config
    implements CommandLineRunner {

  @Autowired
  private X x;

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }
  @EventListener(ApplicationStartedEvent.class)
  public void start() {
    System.out.println("Hello events");
  }
  @Autowired
  public void hack(@NonNull Props props) {
    // if (props == null) throw NPE("😈"); //
//    props.setGate(null);
  }
}
