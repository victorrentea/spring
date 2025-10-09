package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@ComponentScan(basePackages = "no") // disable component scan
@Import({// WHY?
    // a) slightly faster startup
    // b) spring might accidentally detect unwanted classes (eg from picnic libraries you used) that share the same package with tout
    // eg. a lib defining a class victor.training.spring.first.subpa.SomeClass @Service
    Z.class,
    MailServiceImpl.class,
    MailServiceDummy.class,
    X.class,
    Y.class})
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private X x;

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }
}

