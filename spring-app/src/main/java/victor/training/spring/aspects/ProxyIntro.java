package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class ProxyIntro {
//  public static void main(String[] args) {
//    Maths realBean = new Maths();
//    Callback h = new MethodInterceptor() {
//      @Override
//      public Object intercept(Object o, Method method,
//                              Object[] params, MethodProxy methodProxy) throws Throwable {
//        log.debug(method.getName() + "(" + Arrays.toString(params) + ") = ");
//        return method.invoke(realBean, params);
//      }
//    };
//    Maths proxy = (Maths) Enhancer.create(Maths.class, h);
//    SecondGrade secondGrade = new SecondGrade(proxy);
//    secondGrade.mathClass();
//  }
}

//@Slf4j
//class ExtindClasa extends Maths {
//  private final Maths delegate;
//
//  ExtindClasa(Maths delegate) {
//    this.delegate = delegate;
//  }
//
//  @Override
//  public int sum(int a, int b) {
//    log.debug("Sum(" + a + "," + b + ") = ");
//    int r = delegate.sum(a, b);
//    log.debug("r=" + r);
//    return r;
//  }
//
//  @Override
//  public int product(int a, int b) {
//    log.debug("Produs(" + a + "," + b + ") = ");
//    int r = super.product(a, b);
//    log.debug("r=" + r);
//    return r;
//  }
//}

// TODO : ori de cate ori fii-ta din clasa 2 face o op matematica, tu vrei sa o auditezi (param+return)
// ------------------------ // sub aceasta linie nu ai voie  sa atingi codu!
@Service
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }

  public void mathClass() {
//    Maths maths = new Maths(); // 5 new = fara DI = fara proxy-uri
    System.out.println("Oare cu ce Maths lucrez aici? " + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("8 + 8 = " + maths.sum(8, 8));
//    System.out.println("800 x 800 = " + maths.product(800, 800));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}

/*final =1 crash💥*/
@Component
@LoggedMethod
class Maths {
//  @LoggedMethod
  public /*final 2silent ignore😱*/ int sum(int a, int b) {
    return a + b;
  }
  public /*static 3ignored*/ int product(int a, int b) {
    if (b > 100) {
      throw new IllegalArgumentException("NU POT");
    }
    int r = 0;
    for (int i = 0; i < a; i++) {
      r = sum(r, b); // 👑 4:  apelurile LOCALE in aceeasi clasa nu sunt interceptate
    }
    return r;
  }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

