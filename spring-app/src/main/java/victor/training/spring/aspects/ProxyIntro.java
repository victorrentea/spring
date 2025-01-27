package victor.training.spring.aspects;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serial;
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
// ce pot scrie sub linie sa nu mearga interceptia metodei
@Service
class SecondGrade {
  private  Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }
  public void mathClass() {
//    maths = new Maths(); // #5 nu mai intercepteaza pt ca nu mai lucrezi cu proxyul injectat
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("8 + 8 = " + maths.sum(8, 8));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
@Service
/*final class #3 error*/
/*record #4 error ca e final*/
@Logged
class Maths {

  @Secured("ROLE_HR") //A) if (naiVoie) throw new SecurityException();
  // inchipui-o pusa pe o superclasa a lui Math
  @Cacheable("salarii") //B) if (cacheExista) return cache; else return apeleaza;
  public short getSalariu(String angajat) {
    return 0;//
  }
  // CRIMA:
  // 1. vine o HRista si acceseaza salariul lui Victor
  // 2. colegu mai sr+10ani ca Victor si vrea sa vada salariul lui
  // il vede ca @Cacheable are de adineauri valoarea
  // Se intampla buba <=> B ruleaza inaintea lui A


  public /*static #1 ignored*/ int sum(int a, int b) {
    return a + b;
  }
  public /*final #2 ignored/error*/ int product(int a, int b) {
    int result = 0;
    for (int i = 0; i < a; i++) {
      result = sum(result, b); // #6 👑 apelurile locale de metode nu sunt interceptate
    }
    return result;
  }
}

