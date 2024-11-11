package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
  public static void main(String[] args) {
    // WE play the role of Spring here ...
    Maths real = new Maths();
//    Maths decorator = new SubclasaDeLog(real);

    MethodInterceptor callback = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Calling " + method.getName() + " (" + Arrays.toString(args) + ")");
        return method.invoke(real, args);
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, callback);

    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}

// exact asa face Spring, doar ca la runtime
//class SubclasaDeLog extends Maths {
//  private final Maths delegate;
//
//  SubclasaDeLog(Maths delegate) {
//    this.delegate = delegate;
//  }
//
//  @Override
//  public int sum(int a, int b) {
//    System.out.println("sum(" + a + ", " + b + ")");
//    return delegate.sum(a, b);
//  }
//}
// se simte ca si cum interceptam apeluri de metode.
// TODO: orice apel catre Maths sa logeze parametrii; Scrie doar deasupra liniei
// ------------------------ THE LINE ------------------
// scrie ceva sub linie sa opresti proxyurile din a merge
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
//    this.maths = new Maths(); #1
    this.maths = maths;
  }
  public void mathClass() {
    System.out.println("Cine puii tai esti tu Maths? " + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("7 + 6 = " + maths.sum(7, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
/*final crash*/ class Maths {
  // @Secured("ROLE_ADMIN") sau @PreAuthorize("hasRole('ADMIN')") sau @RolesAllowed("ADMIN")
  // @Transactional executa in tranzactie
  // @Cacheable("productCache") - cacheaza rezultatul
  // @Async - executa in alt thread
  // @Retryable - daca da exceptie, reincearca de N ori configurabil...
  public /*final ignored*/ int sum(int a, int b) {
    return a + b;
  }
  public /*static ignored*/ int product(int a, int b) { // nu poti intercepta statice StringUtil.toCamelCase
    int result = 0;
    for(int i = 0; i < b; i++) {
      result = sum(result, a); // ?? apelul local (in aceeasi clasa) nu e interceptat!!!
    }
    return result;
  }
}



// In spring @ puse pe metode pot face:
// 1) IoC - Inversion of Control: @PostConstruct, @Scheduled, @EventListener, @GetMapping
// 2) AOP - Aspect Oriented Programming (interceptie de apeluri): @Transactional, @Secured, @Cacheable, @Async, @Retryable
// aop merge pt ca noi facem DI (luam de la Spring ob real sau proxiat daca trebuie)