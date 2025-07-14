package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import victor.training.spring.altu.X;

//@Import(X.class) // fara auto-scan de @Component
//@ComponentScan("victor.training.spring")
@SpringBootApplication // defineste o app spring boot
@RequiredArgsConstructor
@ConfigurationPropertiesScan
//@EnableConfigurationProperties(Props.class)
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

//  @Qualifier("x1")
  // suficient sa numesti campul/param ctor ca numele beanului dorit
  private final X x1;


//  @Autowired // method-based
//  void init(X x, Y y) {
//    System.out.println("init: " + x + ", " +y);
//  }

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x1.logic());
  }
}

