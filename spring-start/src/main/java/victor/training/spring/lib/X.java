package victor.training.spring.lib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.Y;

import java.util.concurrent.atomic.AtomicInteger;

//@Retention(RetentionPolicy.RUNTIME)
//@Component
//@interface Manager {}

// sa se ocupe springu de asta = "new". cum?!
//@Controller // .jsp
//@RestController // REST
//@Repository // DB
//@Component ce nu se incadreaza in cele de mai sus
//@Manager

//@Configuration // in clase cu configuri de spring, fara pic de logica, de ob cu metode @Bean
// nu ruleaza cod per request

@Slf4j // adauga private static final Logger log = LoggerFactory.getLogger(X.class);
@RestController
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

  // orice state pe care il tine clasa asta e shared intre toate requesturile
  // deci trebuie concurrency control
  private AtomicInteger nrRequesturiDeAzi = new AtomicInteger(0);
  private int prost = 0;
//  String currentUsername;
//  String currentClientId;
//  String currentClientPhone;
  @GetMapping("/request") // GET http://localhost:8080/request
  public void request() {
    nrRequesturiDeAzi.incrementAndGet();
    prost++;// race bug 200 treaduri ar putea rula in acelasi timp aceasta linie
  }

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
  public int f() {
    return 42;
  }
}

//class DacaAsFiSpring {
//  public static void main(String[] args) {
//    Y y = new Y(x);
//    X x = new X(y);
//  }
//}