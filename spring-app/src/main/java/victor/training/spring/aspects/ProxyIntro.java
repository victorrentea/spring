package victor.training.spring.aspects;


import io.micrometer.core.annotation.Timed;
import jakarta.persistence.Cacheable;
import org.hibernate.annotations.Cache;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

public class ProxyIntro {
  public static void main(String[] args) {
    // WE play the role of Spring here ...
    Maths instantaTaCurata = new Maths();

    MethodInterceptor h = (obj, method, args1, proxy) -> {
      System.out.println("Calling " + method.getName() + " with args: " + Arrays.toString(args1));
      return method.invoke(instantaTaCurata, args1);
    };
    // Da-mi Doamne o instanta de subclasa generata a lui Maths pe care orice metoda chem,
    // h trateaza acel apel
    Maths mathsProxy = (Maths) Enhancer.create(Maths.class, h);

    SecondGrade secondGrade = new SecondGrade(mathsProxy);
    secondGrade.mathClass();
  }
}

// logeaza param primit de sum() fara sa modifici nimic sub linie 🔽
// ------------------- LINE -----------------------------------------
class SecondGrade {
  private final Maths maths;

  SecondGrade(Maths maths) {
    this.maths = maths;
  }

  public void mathClass() {
//    Maths maths = new Maths(); // n-ar fi mers proxyurile
    System.out.println("Ce mi s-a injectat:" + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
// Cand nu merg proxyurile?

/*final💥*/
/*record💥*/
class Maths {
//  @Secured("ADMIN")
//  @Transactional
//  @Cacheable
//  @Timed // cat a durat executia functiei in Grafana
  public /*final😶*/ int sum(int a, int b) {

//    if (true) throw new RuntimeException("BUG🐞");
    return a + b;
  }
  public /*static😶*/ int product(int a, int b) {
    int produs=0;
    for (int i = 0; i < b; i++) {
//      Maths proxy = (Maths) AopContext.currentProxy();// 🤖
      // sau @Autowired Maths maths;
      produs = sum(produs, a); // apelul local de metoda (in aceeasi clasa) NU e intereceptat de proxy.
    }
    return produs;
  }
}
class MyUtilHellper {
  public void method() {
    
  }
}

// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

