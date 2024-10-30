package victor.training.spring.lib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.spring.first.Y;

//@Retention(RetentionPolicy.RUNTIME)
//@Component
//@interface Manager {}

// sa se ocupe springu de asta = "new". cum?!
//@RestController // REST
//@Repository // DB
//@Controller // .jsp
//@Component ce nu se incadreaza in cele de mai sus
//@Manager

//@Configuration // in clase cu configuri de spring, fara pic de logica, de ob cu metode @Bean
// nu ruleaza cod per request

@Slf4j // adauga private static final Logger log = LoggerFactory.getLogger(X.class);
@Service
@RequiredArgsConstructor
public class X { // by default singleton
  // rau pt ca:
  // 1. nu poti face 'final'
  // 2. la unit test poate fi complicat sa injectezi mockul de Y
  // daca folosesti @InjectMocks X x; mockito va injecta campurile private
//  @Autowired
//  private Y y;
  // = mock(Y.class);
  private final Y y;

//  @Autowired // nu mai e necesar
//  public X(Y y, Y y2, Y y3, Y y4) {
//    this.y = y;
//    this.y2 = y2;
//    this.y3 = y3;
//    this.y4 = y4;
//  }

//  singleton pattern
//  private static X instance;
//  public static X getInstance() {
//    if (instance == null) {
//      instance = new X();
//    }
//    return instance;
//  }
  public int logic() {
    return 1 + y.logic();
  }
}
