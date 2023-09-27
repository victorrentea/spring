package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

// - Dependency Injection: field, constructor, method
// - Defining beans: @Component & co, @ComponentScan
// - Cyclic dependencies
// - Startup code: PostConstruct, @EventListener, CommandLineRunner
// - Qualifier, bean names
// - Primary
// - Lombok tricks: @RAC, lombok.copyableAnnotations+=
// ----1h
// - Profile (bean & props)
// - @Autowired List<BeanInterface>
// - ApplicationContext#getBean
// - @Scope
// - @Configuration @Bean proxyMethods

// - @Value(${}) + @ConfigurationProperties
@SpringBootApplication
@EnableConfigurationProperties(WelcomeInfo.class)
//@Import() // manual listing the classes to scan

//@ComponentScan(basePackages = "victor.training.spring")
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private X x;

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }
}

@Configuration
class OClasaConfiguration {
//  @Value("${pojo.a}")
//  private String a;
//  @Value("${pojo.b}")
//  private String b;
//  @Bean // fabric de mana si returnez o instanta de pus in containeru spring
//  public Pojo pojo(
//      @Value("${pojo.a}") String a,
//      @Value("${pojo.b}") String b) {
//    return new Pojo()
//        .setA(a)
//        .setB(b);
//  }
  @Bean // fabric de mana si returnez o instanta de pus in containeru spring
  @ConfigurationProperties(prefix = "pojo")
  public Pojo pojo() {
    return new Pojo();
  }
}
///// ----------
// imagine ca e intr-un JAR nu o poti edita
class Pojo {
  private String a;
  private String b;

  public String getA() {
    return a;
  }

  public String getB() {
    return b;
  }

  public Pojo setA(String a) {
    this.a = a;
    return this;
  }

  public Pojo setB(String b) {
    this.b = b;
    return this;
  }

  @Override
  public String toString() {
    return "Pojo{" +
        "a='" + a + '\'' +
        ", b='" + b + '\'' +
        '}';
  }
}