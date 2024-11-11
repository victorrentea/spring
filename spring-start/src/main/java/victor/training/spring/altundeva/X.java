package victor.training.spring.altundeva;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.first.Y;

// face o componenta care va fi manageriata de Spring
// eu, spring o voi instantia si o voi injecta in alte componente care o folosesc

//~~~@Bean pui pe metoda din @COnfiguration
//@Controller (vine din vreama .jsp) <%= %>
//@RestController // orice are de a face cu HTTP/REST

//@Service // logica de business

//@Repository // aici pui SQL, Mongo, Redis, etc

//@Manager // a mea
//@Component // nu e nimic din cele de mai sus, de ob chestii infrastructurale

@Slf4j // log
@Service
@RequiredArgsConstructor // 💖 genereaza exact constructorul de mai jos pt toate campurile finale
// lombok = plugin pt compilator care scrie in .class cod in plus.
public class X {
//  @Autowired // injecteaza un bean de tip Y
//  private Y y; // e 'private'
  // toate frameworkurile java isi bat joc (ignora) de private folosind Reflection
  // Spring, JPA, Jackson, Mongo

  // ❌nu folosi: method injection
//  @Autowired
//  public void setY(Y y) {
//    this.y = y;
//  }

  // 💖 constructor-based injection
  private final Y y;
  private final Y y2;
  private final Y y3;
  private final Y y4;
  private final Y y5;
  private final Y y6;
  private final Y y7;
  private final Y y8;

//  public X(Y y, Y y2, Y y3, Y y4, Y y5, Y y6, Y y7, Y y8) {
//    this.y = y;
//    this.y2 = y2;
//    this.y3 = y3;
//    this.y4 = y4;
//    this.y5 = y5;
//    this.y6 = y6;
//    this.y7 = y7;
//    this.y8 = y8;
//  }

  public int logic() {
    return 1 + y.logic();
  }
}
