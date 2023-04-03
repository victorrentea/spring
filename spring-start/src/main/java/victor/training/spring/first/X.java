package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import victor.training.spring.another.Y;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Import({X.class, Cris.class})
@Configuration
class MyConfig {
//  @Bean
//  public X x(Y y) { // manual bean definition
//    return new X(y);
//  }

//    @Bean
//    public Cris cris(X x, @Value("${mail.sender}") String prop) { // manual bean definition
//      return new Cris(x, prop);
//    }
}

@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Mapper {
}

//@Mapper
//@Component // not usefull application business logic
//@Service // <- put here business logic
//@Repository // <- put here DB access, not used if you extend a Spring Data Repo interface
//@Controller // DEAD from the dark days of Server-side generated HTML jsp, jsf, vaadin. Long live SPA
//@RestController //

// TODO what other annotation can I put here for Spring
//  to register this class as a bean ?
@RequiredArgsConstructor
public class X {
  //  @Autowired
  //  private Y y; // #2 field injection (common in older projs)


  private final Y y; // immutable!❤️, testable w/o magic,
  private Y y2;
  private ApplicationContext applicationContext;

  // #1 constructor injection
//  public X(Y y) {
//    this.y = y;
//  }

  // #3 method injection (rarely used)
  @Autowired
  public void init(Y y, ApplicationContext applicationContext) {
    this.y2 = y;


    this.applicationContext = applicationContext;
  }
//  @Autowired
//  SomeClass bad;
  public int logic() { // called from a REST API
    // BAD PRACTICE:
    System.out.printf("At runtime, 1 day later to increase the joy... ");
//    SomeClass bad = applicationContext.getBean(SomeClass.class);
    return 1 + y.logic();
  }
}
class SomeClass {

}

