package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

// declari aceasta clasa ca Bean in spring, o va instantia O DATA
//@Service // = business logic  BR-4581
//@Repository // access la DB a ta
//@RestController // @GetMapping REST
//@Controller // = HTML de pe server cu .jsp .jsf vaadin thymeleaf
@Component // cand nimic de sus nu merge. aka gunoi/util
//@SpringBootApplication
public class X {
  @Autowired
  private Y y; // #2 field injection

  // #3 method injection (rarely used)
  // @Autowired public void init(Y y) {this.y2 = y;}

  public int logic() {
    return 1 + y.logic();
  }
}
