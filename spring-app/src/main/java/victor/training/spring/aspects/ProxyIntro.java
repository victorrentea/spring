package victor.training.spring.aspects;


public class ProxyIntro {
  public static void main(String[] args) {
    // WE play the role of Spring here ...
    Maths maths = new Maths();
    MathsDecorator mathsDecorator = new MathsDecorator(maths);
    SecondGrade secondGrade = new SecondGrade(mathsDecorator);
    secondGrade.mathClass();
  }
}
class MathsDecorator extends Maths {
  private final Maths maths;
  MathsDecorator(Maths maths) {
    this.maths = maths;
  }
  public int sum(int a, int b) {
    System.out.println("sum(" + a + ", " + b + ")");
    return maths.sum(a, b);
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
    System.out.println("Obiectiv DI: sa-ti injectezi dependente decorate:" + maths.getClass());
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

