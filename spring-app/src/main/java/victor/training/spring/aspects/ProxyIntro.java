package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
  public static void main(String[] args) {
    // WE play the role of Spring here ...
    Maths reala = new Maths();

    Callback callback = new MethodInterceptor() {
      @Override
      // se trateaza orice apel al unei fct publice din Maths
      public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object r = method.invoke(reala, args);
        System.out.println("Called " + method.getName() + " " + Arrays.toString(args) +" = " +r);
        return r; // <--
      }
    };
    //creeaza o subclasa asa cum e mai jos (comentat)
    Maths proxy = (Maths) Enhancer.create(Maths.class, callback);

    SecondGrade secondGrade = new SecondGrade(proxy);

    secondGrade.mathClass();
  }
}
//Exact asa face springu!: iti ia clasa ta marcata cu eg @service si o subclaseaza,
// @Overridand toate metodele publice.
// Ulterior, oricine va avea nevoie sa fie injectat cu un Maths, va primi o instanta din aceasta subclasa
//class UnaCareSaOExtinda extends Maths {
//  private final Maths delegate;
//
//  public UnaCareSaOExtinda(Maths delegate) {
//    this.delegate = delegate;
//  } // DECORATOR PATTERN
//  @Override
//  public int sum(int a, int b) {
//    int r = delegate.sum(a, b);
//    System.out.println("sum("+a+","+b+")="+r);
//    return r;
//  }
//
//  @Override
//  public int product(int a, int b) {
//    int r = delegate.product(a, b);
//    System.out.println("produs("+a+","+b+")="+r);
//    return r;
//  }
//}


// sa pp ca Emma are un tatic paranoic care vrea sa vada toate calculele facute la scoale sa le verifice/auditeze
// taticu nu vrea sa se prinda copilul
// TODO interceptezi orice apel facut de SecondGrade la orice metoda din Maths
//  sa printezi param si return value (pt audit de tatic)
// ------------------------ NU AI VOIE SA SCRII NIMIC SUB LINIA ASTA
class SecondGrade {
  private final Maths maths;
  public SecondGrade(Maths maths) {
    this.maths = maths;
  }
  public void mathClass() {
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
    System.out.println("4 x 4 = " + maths.product(4, 4));
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


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

