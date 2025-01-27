package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.Y;

import java.util.List;

// se creaza un bean de spring = o instanta care fa fi mageuita de containerul de Spring
// Spring face "new X"
// cu oricare de mai jos
//@Controller // raspunde la HTTP cu HTML folosind .jsp/velocity/freemarker
//@RestController // expune API REST
//@Service // daca face business logic - raison d'etre a aplicatiei tale.
//@Repository // daca face DB. ! nu e necesara pe interfete care exting on interfata Spring Data
@Component // tot ce nu e nimic de mai sus:eg. security sh*t, spring-related infra

//@Configuration // definitii de beanuri @Bean
public class X {
  // #1 DI prin campuri
//  @Autowired // o gasiti pe SO + teste unitare @SpringBootTest
//  private Y y;

  // #2 DI prin ctor, nu e necesara @Autowired
  private final Y y;
  public X(/*@Lazy*/ Y y) { // ori de cate ori se poate
    this.y = y;
  }

//  private Y y;
//  @Autowired // #3 DI setter niciodata, decat daca
//  public void setY(Y y) {
//    y.logic();
//  }

  @Autowired
  private ApplicationContext applicationContext;

  public int logic() {
//    System.out.println("La runtime");
//    Y y = applicationContext.getBean(Y.class); // programmatic GET
    return 1 + y.logic();
  }

  @Autowired
  private List<Offer> toate;

  @PostConstruct
  public void aplicaOferte() {
    System.out.println("Aplic oferte");
    for (Offer offer : toate) {
      offer.aplicaDiscount("cos");
    }
  }
}

interface Offer {
//  boolean seApplica(String cos);
  int aplicaDiscount(String cos);
}
@Service
@Order(1)
class Oferta1 implements Offer {
  @Override
  public int aplicaDiscount(String cos) {
//    if (bf)
    System.out.println("Oferta1");
    return 0;
  }
}
@Order(3)
@Service
class Oferta2 implements Offer {
  @Override
  public int aplicaDiscount(String cos) {
    System.out.println("Oferta2");
    return 0;
  }
}
@Order(1)
@Service
class XOferta3 implements Offer {
  @Override
  public int aplicaDiscount(String cos) {
    System.out.println("Oferta3");
    return 0;
  }
}