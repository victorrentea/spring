package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.Y;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// face clasa un Bean de Spring
//@RequiredArgsConstructor

//@Controller // nu se mai folosesc, vin din vremea .jsp, .jsf, VAADIN,  generam HTML din server cu java
//@RestController // API REST
//@Repository // DB access

//@Component // tot ce ramane, gunoiu
//@MessageListener // MQ
//@Configuration

//@Bean // nu aici ci pe o metoda dintr-o clasa @Configuration
//@Mapper
@Service // = e o clasa cu logica
public class X {
//  @Autowired
//  private Y y; // #2 field injection cu reflection chiar daca e privat

  private final Y y;
  public X(Y y) { // #1 constructo injection e recomandat. de ce: mai usor de testat, si mai imutabil❤️
    this.y = y;
  }

//   #3 method injection (rarely used)
//   @Autowired
//   public void init(Y y) {this.y2 = y;}

  public int logic() {
    return 1 + y.logic();
  }
}
