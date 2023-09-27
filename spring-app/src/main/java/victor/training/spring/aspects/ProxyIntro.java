package victor.training.spring.aspects;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

public class ProxyIntro {
  public static void main(String[] args) {
    // WE play the role of Spring here ...
    Maths reala = new Maths();

    Callback callback = new MethodInterceptor() {
      @Override
      // se trateaza orice apel al unei fct publice din Maths
      public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // are dreptu useru sa cheme functia ? @Secured("ROLE_ADMIN") ?

//        transaction.start @Transactional

        long t0 = currentTimeMillis();
        Object r = method.invoke(reala, args);
        long t1 = currentTimeMillis(); // @Timed

//        transaction.commit

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
    this.maths = maths; // spring ii injecteaza un Proxy care sta in fata instantei reale de Maths
  }
  public void mathClass() {
//    Maths maths = new Maths(); // (4) mi-am bagat picioru in DI, am facut NEW
    System.out.println("Cine esti tu: " + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
    System.out.println("4 x 4 = " + maths.product(4, 4));
  }
}
// CE pot face in clasa Maths ca sa nu mai mearga proxyurile (sa nu mai fie interceptat metoda)
/*final CRASH(1)*/ class Maths {
//  @Secured("ROLE_ADMIN")
//  @Transactional
//  @Cacheable("date-critice") // daca asta merge primu, ar putea intoarce date unui user care nu e ADMIN
//  @Secured("ROLE_ADMIN")

  public /*final SILENT IGNORE(2)*/ int sum(int a, int b) {
//    if (true) throw new IllegalArgumentException(); // se vad proxy-urile in exceptii ca $$EnhancerByCGLIB$$
    return a + b;
  }
  public /*static NOT OVERRIDABLE(3)*/ int product(int a, int b) {
    int rezultat = 0;

    for (int i = 0; i < a; i++) {
      // apelul local in cadrul aceluiasi obiect NU TREC PRIN PROXY(5👑)
      rezultat = sum(rezultat, b);
    }
    return rezultat;
  }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

