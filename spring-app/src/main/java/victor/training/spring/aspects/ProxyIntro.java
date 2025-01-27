package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths real = new Maths();
    Callback callback = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] params, MethodProxy proxy) throws Throwable {
        System.out.println("Method " + method.getName() + " called with params " + Arrays.toString(params));
        long t0 = currentTimeMillis();
        try {
          return method.invoke(real, params);
        } finally {
          long t1 = currentTimeMillis();
          System.out.println("Method " + method.getName() + " took " + (t1 - t0) + " ms");
        }
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, callback);

    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}
//class MathsProxy extends Maths {
//  Maths maths = new Maths();
//  public int sum(int a, int b) {
//    System.out.println("sum called with " + a + " and " + b);
//    return maths.sum(a, b);
//  }
//  public int product(int a, int b) {
//    System.out.println("product called with " + a + " and " + b);
//    return maths.product(a, b);
//  }
//}
// afiseaza param fiecarui apel de metoda in Maths, fara a scrie nimic sub linie
// ------------------- LINIE ------------------
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }
  public void mathClass() {
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("8 + 8 = " + maths.sum(8, 8));
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

