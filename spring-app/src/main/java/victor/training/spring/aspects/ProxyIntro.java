package victor.training.spring.aspects;

import io.micrometer.core.annotation.Timed;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths real = new Maths();
    Callback callback = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Intercepted " + method.getName() + " " + Arrays.toString(args));
        return method.invoke(real, args); // lasa apelul sa se duca la metoda reala
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, callback);
    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}

// fara a modifica nimic sub linia asta, printati fiecare
// apel de functie sum/produs cu parametrii ei
// pe care-l face SecondGrade (sa interceptam apelul)
// EXACT O ASTFEL DE CLASA VA GENERA SPRINGUL PENTRU FIECARE BEAN PE CARE VREA SA-L PROXIEZE
//class MathsIntercept extends Maths { // ~ un fel de DECORATOR DESIGN PATTERN
//  private final Maths delegate;
//  MathsIntercept(Maths delegate) {
//    this.delegate = delegate;
//  }
//  public int sum(int a, int b) {
//    System.out.println("Intercepted sum(" + a + ", " + b + ")");
//    return delegate.sum(a, b);
//  }
//
//  public int product(int a, int b) {
//    System.out.println("Intercepted product(" + a + ", " + b + ")");
//    return delegate.product(a, b);
//  }
//}

// -----------------------------------------
@Service
class SecondGrade {
  private final Maths maths;

  SecondGrade(Maths maths) {
    this.maths = maths;
  }

  public void mathClass() {
    System.out.println("APelul poate fi proxiat doar cand e facut pe o referinta" +
                       "injectata de spring, ca asta:\n" + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 2 = " + maths.product(4, 2));
  }
}
// CUM POT SA STRIC PROXIEREA? Sa-l opresc pe Spring sa faca proxy-uri?
@Service
/*final=CRASH*/
class Maths {
  // acestea sunt utilizari ale proxyurilor
//  @Transactional // tot ce face cu DB va fi ATOMIC (tranzactionat impreuna)
//  @Secured("ROLE_ADMIN") // nu da voie apelului decat daca esti ADMIN
//  @Cacheable("sume") // daca chemi a doua oara metoda asta cu aceiasi parametrii, va lua din cache din memo
//  @Async // executa aceasta metoda pe alt thread
//  @Timed // masoara durata executiei (gen t0,t1) si o raporteaza ca metrica
//  @RateLimiter limiteaza de cate poti chema aceasta metoda max (10/sec)
  /*final=IGNORE*/
  /*static=IGNORE*/
  @Logged
  public int sum(int a, int b) {
    return a + b;
  }

  @Logged
  public int product(int a, int b) {
//    return a * b;
    int r = 0;
    for (int i = 0; i < a; i++) {
      r = sum(r, b); // TZEAPA👑👑: NU MERG PROXY-URILE cand apelul e in aceeasi clasa.
    }
    return r;
  }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

