package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import victor.training.spring.first.subp.Y;

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

  public int logic() {
    return 1 + y.logic();
  }
}

@Component
@Retention(RUNTIME) // stops javac from removing it at compilation
@interface Facade {}