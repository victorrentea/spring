package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import victor.training.spring.Y;

// declari aceasta clasa ca Bean in spring, o va instantia O DATA
//@Service // = business logic  BR-4581
//@Repository // access la DB a ta
//@RestController // @GetMapping REST
//@Controller // = HTML de pe server cu .jsp .jsf vaadin thymeleaf
//@SpringBootApplication

@Slf4j
@Component // cand nimic de sus nu merge. aka gunoi/util
@RequiredArgsConstructor // lombok = dovada ca java e '95
public class X {
  private final Y y;

  public int logic() {
    return 1 + y.logic();
  }
}
