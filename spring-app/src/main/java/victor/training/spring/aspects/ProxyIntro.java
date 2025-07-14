package victor.training.spring.aspects;

import lombok.SneakyThrows;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths maths = new Maths();

    MethodInterceptor callback = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Calling method: " + method.getName() +
                           " with args: " + Arrays.toString(args));
        long t0 = currentTimeMillis();
        var r = method.invoke(maths, args);
        long t1 = System.currentTimeMillis();
        System.out.println("Took ms: "+ (t1-t0));
        return r;
      }
    };// CTRL-SPACE
    Maths proxy = (Maths) Enhancer.create(Maths.class, callback);
    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}
//class MathsWithLog extends Maths {
//  @Override
//  public int sum(int a, int b) {
//    System.out.println("sum called with: " + a + ", " + b);
//    return super.sum(a, b);
//  }
//
//  @Override
//  public int product(int a, int b) {
//    System.out.println("product called with: " + a + ", " + b);
//    return super.product(a, b);
//  }
//}
// TODO printati parametrii dati metodelor din Maths
//  fara sa schimbati nimic sub LINIE
//  Hint @alex: inheritance pe Maths
// ------------------- LINIE ------------------
@Service
class SecondGrade {
  private  Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }
  public void mathClass() {
    System.out.println(maths.getClass());
//    maths = new CustomerEntity();
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
//******* CE trebuie sa fac sub aceasta LINIE pt ca sa NU mai mearga interceptia metodelor
@Logged
@Service
/*final💥*/class Maths {
  /*final😶*/ public int sum(int a, int b) {
    return a + b;
  }
  /*static😶 nu pe Util*/
  public int product(int a, int b) {
//    return a * b;
    int r=0;
    for (int i = 0; i < a; i++) {
      r = sum(r, b); // 👑NU MERG pe apeluri interne clasei (met locale)
    }
    return r;
  }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

