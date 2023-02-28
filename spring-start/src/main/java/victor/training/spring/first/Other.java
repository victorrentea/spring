package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class Other implements CommandLineRunner {
//  @Value("${prop}")
//  private String p;

  @PostConstruct // run this method after you injected all dependencies
//  @Transactional the annotation DOES NOT WORK vecause the proxy is not yet setup.
  public void hello() {
    System.out.println("Hello at startup");
  }

  @EventListener(ApplicationStartedEvent.class)
  public void event() {
    System.out.println("Started Event");
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("From CommandLineArgs with acess to command line args " + Arrays.toString(args));
  }
}
