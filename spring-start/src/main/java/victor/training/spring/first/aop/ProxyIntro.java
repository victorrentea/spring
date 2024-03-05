package victor.training.spring.first.aop;

import org.springframework.stereotype.Service;

public class ProxyIntro {
  public static void main(String[] args) {
    // WE play the role of Spring here ...
    Maths maths = new Hack();
    SecondGrade secondGrade = new SecondGrade(maths);

    secondGrade.mathClass();
  }
}
class Hack extends Maths {
  @Override
  public int sum(int a, int b) {
    System.out.println("Yey!");
    return super.sum(a, b);
  }

  @Override
  public int product(int a, int b) {
    System.out.println("Yey!");
    return super.product(a, b);
  }
}
// Challenge: without touching code below this line, print a message to console
// when second grade calls any method of Maths
// --------------------------------------
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }

  public void mathClass() {
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}

class Maths {
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

