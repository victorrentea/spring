package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import victor.training.spring.first.subp.Y;

import javax.annotation.PostConstruct;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

// declari aceasta clasa ca Bean in spring, o va instantia O DATA
//@Service // = business logic  BR-4581
//@Repository // access la DB a ta
//@RestController // @GetMapping REST
//@Controller // = HTML de pe server cu .jsp .jsf vaadin thymeleaf
//@SpringBootApplication
//@Facade

@Slf4j
@Component
@RequiredArgsConstructor // lombok = dovada ca java e '95
public class X {
  private final Y y;
//  @Qualifier("a") // nu mai e necesar daca numesti punctul de injectie
//  (ctor arg sau camp @Autowired) exact ca numele beanului dorit
  private final OClasaDintrunJar a;
  @Value("${db.password}")
  private final String dbPass;


  public int logic() {
    a.method();
    return 1 + y.logic();
  }

  @PostConstruct
  public void atStartup() {
    System.out.println("DB PASS: " + dbPass);
  }

}
// breaking news: beanurile spring au toate nume!!!
// - daca beanul e creat cu @Componenty & friends ClasaMea, numele beanului este : "clasaMea"
// - daca beanul e definit cu @Beanl, numele beanului este numele metodei
@Configuration
class ConfiguMeu {
  @Bean
  public OClasaDintrunJar a() {
    return new OClasaDintrunJar();
  }
  @Bean
  public OClasaDintrunJar b() {
    return new OClasaDintrunJar();
  }
}

// -- sub aceasta linie e cod dintr-o librarie
class OClasaDintrunJar {
  public void method() {

  }
}







@Component
@Retention(RUNTIME) // stops javac from removing it at compilation
@interface Facade {}