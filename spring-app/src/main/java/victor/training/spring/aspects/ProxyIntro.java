package victor.training.spring.aspects;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Supplier;

import static java.lang.System.currentTimeMillis;

@Slf4j
public class ProxyIntro {
  public static void main(String[] args) {
    Maths maths = new Maths();
//    MathsLoggingProxy proxy = new MathsLoggingProxy(maths);
    Callback h = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info(method.getName() + " cu " + Arrays.toString(args));
        return method.invoke(maths, args);
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, h);
    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}
//@Slf4j
//class MathsLoggingProxy extends Maths {
//  private final Maths delegate;
//  MathsLoggingProxy(Maths delegate) {
//    this.delegate = delegate;
//  }
//  @Override
//  public int sum(int a, int b) {
//    log.info("sum cu "+a+","+b);
//    return delegate.sum(a, b);
//  }
//  @Override
//  public int product(int a, int b) {
//    log.info("product cu "+a+","+b);
//    return delegate.product(a, b);
//  }
//}
// printati parametrii metodei sum+produs cand e chemata, fara a umbla sub linie
// ------------------- LINE --------------------------
// ce pot face sub linie ca spring sa nu-mi poata intercepta apelurile de metode
@Service
class SecondGrade {
  private  Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }
  public void mathClass() {
    System.out.println(maths.getClass().getName());
//    maths=new Maths(); // coleg confuz care nu stie ca nu 'new' clasele manageuite de spring
    int r = measure(()->maths.sum(8,4));
//    transactionTemplate.execute(s-> {
//      // INSERT1
//      // INSERT2
//    });
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }

  private int measure(Supplier<Integer> s) {
    long t0 = currentTimeMillis();
    Integer r = s.get();
    long t1 = currentTimeMillis();
    System.out.println("A luat "+(t1-t0)+" ms");
    return r;
  }
}
@Component
@Slf4j
/*❌final💥*/
/*❌record Maths() {💥*/
@Logged
class Maths {
  public /*static~Util😶*/ /*😱😱😱final😶*/ int sum(int a, int b) {
    return a + b;
  }
  public int product(int a, int b) {
//    return a * b;
    int r = 0;
    for (int i = 0; i < a; i++) {
      r = thisBean.sum(r, b); // 👑❌❌❌❌tzeapa: apelul in aceeasi clasa NU POATE FI INTERCEPTAT!!
    }
    return r;
  }
  @Autowired
  Maths thisBean; // Doamne-fereste, dar totusi...
}
// Spring vrea sa-ti interecepteze apelurile de metode cand andotezi metodele cu....:
// - @Transactional: tx.start > metodaTa() > tx.commit
// - @Secured("ROLE_ADMIN"): daca n-ai rolul, throws exception
// - @Timed/@Observed: t0 > metodaTa() > t1 si retine cat a durat /actuator/prometheus
// - @Cacheable





// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect









// 2 x 3 = 2 + 2 + 2






