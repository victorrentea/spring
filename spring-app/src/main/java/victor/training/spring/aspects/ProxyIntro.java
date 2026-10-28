package victor.training.spring.aspects;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

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
    System.out.println("Ce mi s-a injectat:" + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
// cum stric proxyurile?

class  Maths {
  public int sum(int a, int b) {
    return a + b;
  }

  public int product(int a, int b) {
    return a * b;
  }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

