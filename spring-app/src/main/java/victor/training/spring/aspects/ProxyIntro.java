package victor.training.spring.aspects;

import static java.lang.System.currentTimeMillis;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths maths = new Maths();
    Proxy proxy = new Proxy(maths);
    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}
// this class is GENERATED at runtime by Spring using CGLIB Enhancer, and it extends the original class (Maths) and overrides its methods to add extra behavior (logging, timing)
class Proxy extends Maths { // pretend is YOUR bean, when in fact it's a proxy that extends the original bean
  private final Maths wrapped;
  Proxy(Maths wrapped) {
    this.wrapped = wrapped;
  }
  public int sum(int a, int b) {
    System.out.println("sum called with " + a + " and " + b);
    long t0 = currentTimeMillis();
    int r = wrapped.sum(a, b);
    long t1 = currentTimeMillis();
    System.out.println("sum took " + (t1 - t0) + " ms and returned " + r);
    return r;
  }
  public int product(int a, int b) {
    System.out.println("product called with " + a + " and " + b);
    return wrapped.product(a, b);
  }
}
// TODO Print the parameters that the methods of maths receive when invoked WITHOUT CHANGING ANY CODE BELOW THE LINE🔽
// ------------------- LINE -------------------------------------
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) { // DI framework can inject a proxy here,
    // and the proxy will be able to intercept calls to the methods of maths and add extra behavior (logging, timing)
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

