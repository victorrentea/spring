package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths realMaths = new Maths();
//    Proxy proxy = new Proxy(realMaths);
    Callback h = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println(method.getName() + " with params: "+ Arrays.toString(args));
        var result  =method.invoke(realMaths, args);
        return result;
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, h);// CodeGenLIB
    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}
// this class is GENERATED at runtime by Spring using CGLIB Enhancer, and it extends the original class (Maths) and overrides its methods to add extra behavior (logging, timing)
//class Proxy extends Maths { // pretend is YOUR bean, when in fact it's a proxy that extends the original bean
//  private final Maths wrapped;
//  Proxy(Maths wrapped) {
//    this.wrapped = wrapped;
//  }
//  public int sum(int a, int b) {
//    System.out.println("sum called with " + a + " and " + b);
//    long t0 = currentTimeMillis();
//    int r = wrapped.sum(a, b);
//    long t1 = currentTimeMillis();
//    System.out.println("sum took " + (t1 - t0) + " ms and returned " + r);
//    return r;
//  }
//  public int product(int a, int b) {
//    System.out.println("product called with " + a + " and " + b);
//    return wrapped.product(a, b);
//  }
//}
// ✅ Print the parameters that the methods of maths receive when invoked WITHOUT CHANGING ANY CODE BELOW THE LINE🔽
// ------------------- LINE -------------------------------------
// TODO MESS UP SOMETHING BELOW THE LINE TO STOP PROXY FROM WORKING ⭐️
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) { // DI framework can inject a proxy here,
    // and the proxy will be able to intercept calls to the methods of maths and add extra behavior (logging, timing)
    this.maths = maths;
  }
  public void mathClass() {
    System.out.println("What type have I actually been injected? "+ maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
/*❌#1 final class*/
//❌#2 record Maths(/*MoreDeps deps*/) = final{
@Service
class Maths {
  // @Secured(ROLE_ADMIN)
  // Calling this method from outside of this class would ensure that the user is admin,
  // but if the method is called from within the class, no check is performed
  public /*😶#3 static*/ int sum(int a, int b) {
    return a + b;
  }
  public /*😶#4 final*/ int product(int a, int b) {
//    return a * b;
    // 4 x 3 = 4 + 4 + 4
    int r= 0;
    for (int i = 0; i < a; i++) {
      r = this.sum(r, b); // ❌#5 👑 local method calls don't get intercepted (@ don't w
    }
    return r;
  }
}




// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

