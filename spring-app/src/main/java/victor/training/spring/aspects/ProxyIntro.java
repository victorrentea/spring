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
//    Maths proxy = new MathsProxy(real);
    Callback callback = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Method " + method.getName() + " called with " + Arrays.toString(args));
        return method.invoke(real, args); // call the real method
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, callback);
    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}
// Mockito @Mock mock( and Spring use these technique to hack your code
//class MathsProxy extends Maths {
//  private final Maths delegate;
//  MathsProxy(Maths delegate) {
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
//    return delegate.product(a, b); // WHAT SPRING DOES
//    return this.product(a, b); // NOT WHAT SPRING DOES
//  }
//}
// whenever SecondGrade calls sum() or product(), print the params, without changing any code below the line
// ------------------- LINE ------------------
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }
  public void mathClass() {
//    Maths maths = new Maths();😶  new MyImmutable, new MyRecord, new Dto, new JooqRecord
    System.out.println("What maths class did Spring injected me here? " + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
    System.out.println("4 x 4 = " + maths.curveIntegral(4, 4));
  }
}
// Edit some code below this line that will stop the proxy from working
/*final🚫*/
/*record🚫*/
class Maths {
  // @Async starts the method on another thread
  // @Timed/@Observed // expose a metric on how long this method took
  // @Transactional commit all SQL interactions of this method ATOMICALLY
  // @Secured(ROLE_ADMIN) // only allow call if your user does have ADMIN ROLE
   // ✅ called from OUTSIDE of this class works
   // 🚫 called from INSIDE of this class does not work- allows ANY role to call it
  public /*final😶*/ int sum(int a, int b) {
    return a + b;
  }
  public /*static😶*/ int product(int a, int b) {
//    return a * b;
    System.out.println("What class am I now in ? " + this.getClass());
    int result = 0;
    for (int i = 0; i < a; i++) {
      result = sum(result, b); // 👑THE BIGGEST PITFALL OF SPRING!
      // local method calls don't get INTERCEPTED ❌❌❌❌
    }
    return result;
  }
  public int curveIntegral(int a, int b) {
    return 42;
  }
}




// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

