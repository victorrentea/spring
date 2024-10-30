package victor.training.spring.aspects;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths real = new Maths(); // instanta curata din beanul tau
    SubclasaPtLog proxy = new SubclasaPtLog(real); // subclasandu-ti clasa ta
    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}

class SubclasaPtLog extends Maths {
  private final Maths delegate;
  public SubclasaPtLog(Maths delegate) {
    this.delegate = delegate;
  }

  @Override
  public int sum(int a, int b) {
    System.out.println("sum " + a + " and " + b);
    return delegate.sum(a, b);
  }
  @Override
  public int product(int a, int b) {
    System.out.println("product " + a + " and " + b);
    return delegate.product(a, b);
  }
}
// orice metoda se cheama in Maths, logeaza-i parametrii, fara sa modifici nimic sub linie
// ------------------------ THE LINE ------------------
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

