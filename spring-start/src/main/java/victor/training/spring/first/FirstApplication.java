package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;

import java.util.Properties;

@SpringBootApplication
@ComponentScan(basePackages = "no") // disable component scan
@Import({// WHY?
    // a) slightly faster startup
    // b) spring might accidentally detect unwanted classes (eg from picnic libraries you used) that share the same package with tout
    // eg. a lib defining a class victor.training.spring.first.subpa.SomeClass @Service
    Z.class,
    MailServiceDummy.class,
    X.class,
    Y.class,
    MyConfig.class})
@EnableConfigurationProperties(Props.class)
public class FirstApplication implements CommandLineRunner {
  @Autowired
  private ApplicationContext applicationContext;

  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private X x;

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {

    System.out.println("App started OK 🎉 " +
                       applicationContext.getEnvironment()
                           .getProperty("props.gate") // DONT! risky vs typos, changes in yaml
    );
  }

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }
}

