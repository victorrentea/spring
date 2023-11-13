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
    Callback h = new MethodInterceptor() {
      @Override
      public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object r = method.invoke(real, args);
        System.out.println("Call intercepted: " + method.getName() + Arrays.toString(args)  + "="+ r);
        return r;
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, h);
    SecondGrade secondGrade = new SecondGrade(proxy);

    secondGrade.mathClass();
  }
}
//class MathsProxy extends Maths {
//  private final Maths delegate;
//  MathsProxy(Maths delegate) {
//    this.delegate = delegate;
//  }
//  @Override
//  public int sum(int a, int b) {
//    int r = delegate.sum(a, b);
//    System.out.println("sum(" + a + "," + b + ")="+r);
//    return r;
//  }
//  @Override
//  public int product(int a, int b) {
//    int r = delegate.product(a, b);
//    System.out.println("product(" + a + "," + b + ")="+r);
//    return r;
//  }
//}

// TODO sa logam orice operatie face Maths, dar nu ai voie sa scrii cod sub linia asta:
// -------------------------------------------------------------
class SecondGrade {
  private final Maths maths;

  SecondGrade(Maths maths) {
    this.maths = maths;
  }

  public void mathClass() {
    System.out.println("Oare ce Math class mi-a fost injectat? " +maths.getClass());
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
