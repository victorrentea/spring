package victor.training.spring.aspects;

import io.github.resilience4j.retry.annotation.Retry;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;
import java.io.Serial;
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
// ce pot face sub linie (in codul meu) sa-l stric proxy-urile
@Service
class SecondGrade {
  private  Maths maths;

  SecondGrade(Maths maths) {
    this.maths = maths;
  }
//  @Secured("ROLE_LOAN_APPROVAL")
//  @Cacheable
//  @Transactional
  public void approveLoan() {}

  public void mathClass() {
    approveLoan(); // nu trece prin proxy daca o chemi local
//    maths = new Maths(); // #5 daca nu iei din spring obiectul, nu-ti vei lua un proxy
    // mai sunt cazuri cand ai vrea sa interceptezi o metoda de pe un @Entity de hibernate. NU MERGE
    // !! Poti intercepta doar metode de pe beanuri manageuite de Spring
    System.out.println("Oare ce Math class mi-a fost injectat? " +maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}

// final #2 -> startup crash: morala nu-ti face clasele de @Service&friends final/@Value/record(java17)
@Service
class Maths {
  public /*#3 final -> silent skip this method */ int sum(int a, int b) {
    return a + b;
  }

  @Cacheable("produse")
  public /*#4 static -> silent skip this method*/ int product(int a, int b) {
//    return a * b;
    if(true)throw new IllegalArgumentException();
    int result = 0;
    for (int i = 0; i < a; i++) {
      result = sum(result, b); // #1 👑 apelul unei metode locale in aceeasi clasa
                  // NU POATE FI INTERCEPTAT de catre proxy!!
      // nu merg adnotarile de pe metoda!!
    }
    return  result;
  }
}
// CGLIB Enhancer subclaseaza Maths si override toate metodele
