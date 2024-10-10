package victor.training.spring.aspects;


import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths real = new Maths();
//    SecondGrade secondGrade = new SecondGrade(new MathsLoggingProxy(real));

    //spring (not you) will do:
    Callback callback = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Method " + method.getName() + " called with args " + Arrays.toString(args));
        return method.invoke(real, args);
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, callback);

    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}
//class MathsSecuredProxy extends Maths { // cross-cutting concern: security
//class MathsTxProxy extends Maths {
// exactly this class will get generated by Spring at RUNTIME!!
//class MathsLoggingProxy extends Maths // class-proxying
//{
//  private final Maths delegate;// the real maths or yet another proxied instance
//
//  MathsLoggingProxy(Maths delegate) {
//    this.delegate = delegate;
//  }
//  @Override
//  public int sum(int a, int b) {
//    System.out.println("sum(" + a + ", " + b + ")");
//    return delegate.sum(a, b);
//  }
//  @Override
//  public int product(int a, int b) {
//    System.out.println("product(" + a + ", " + b + ")");
//    return delegate.product(a, b);
//  }
//}

//class GeneratedProxy implements ServiceInterface{} // interface-proxyinh
// fun String.hello() = "Hello, " + this
// "abc".hello()

//CR: we need to LOG the arguments of sum() and product()
// WITHOUT TOUCHING ANY CODE BELOW THE LINE
// ------------------------ THE LINE ------------------
// the code I wrote
class SecondGrade {
  private final Maths maths;

  SecondGrade(Maths maths) {
    this.maths = maths;
  }

  public void mathClass() {
    System.out.println("What maths class WAS I injected? " + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}

class Maths {
  public int sum(int a, int b) {
    throw new RuntimeException("For @Ishan");
//    return a + b;
  }

  public int product(int a, int b) {
    return a * b;
  }
}


//  @Timed // exposes a metric on how long this method takes at /actuator/promehteus
//  @Transactional // start a tx before and commits/rollback after
//  @Secured("ROLE_CAN_SUM_NUMBERS") // requires the requesting us er to have these roles
// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

