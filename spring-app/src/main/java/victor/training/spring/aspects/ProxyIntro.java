package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths real = new Maths();
//    Maths maths = new LoggingProxy(real);
    Callback handler = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println(method.getName() + " called with " + Arrays.toString(args));
        return method.invoke(real, args);
      }
    };
    Maths maths = (Maths) Enhancer.create(Maths.class, handler); //scrie codul scris de noi dar dinamic la runtime
    SecondGrade secondGrade = new SecondGrade(maths);
    secondGrade.mathClass();
  }
}
//class LoggingProxy extends Maths {
//  private final Maths delegate;
//  public LoggingProxy(Maths delegate) {
//    this.delegate = delegate;
//  }
//  @Override
//  public int sum(int a, int b) {
//    System.out.println("sum called with " + a + " and " + b);
//    return delegate.sum(a, b);
//  }
//  @Override
//  public int product(int a, int b) {
//    System.out.println("product called with " + a + " and " + b);
//    return delegate.product(a, b);
//  }
//}
// logeaza param primiti de metodele din Maths fara sa modifici cod sub linie
// sau: ti-e #rusine sa copii cod de 7 ori
// ------------------- LINE ------------------
// fa ceva sub linie sa nu mai mearga interceptia de metode
@Service
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }
  public void mathClass() {
    /*4:var maths = new Maths();*/
    System.out.println("Eu am cerut o instante de Maths, oare ce-am primit? " + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 7 = " + maths.sum(6, 7));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
@Service
@Logged
/*3:final💥*/ class Maths {
  // @Transactional =  begin inainte, COMMIT dupa
  // @Secured("ROLE_ADMIN") = iti arunca ex daca callerul nu e ADMIN
  // @Timed = masoara timpul de executie
//  @Secured("ROLE_ADMIN")
  public /*1:final😶*/ int sum(int a, int b) {
    return a + b;
  }


  public /*2:static😶*/ int product(int a, int b) {
//    return a * b;
    int result = 0;
    for (int i = 0; i < a; i++) {
      result = sum(result, b); // 👑 apel local in aceeasi clasa NU e interceptat!
    }
    return result;
  }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

