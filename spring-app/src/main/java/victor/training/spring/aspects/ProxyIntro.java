package victor.training.spring.aspects;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths real = new Maths(); // instanta curata din beanul tau
//    SubclasaPtLog proxy = new SubclasaPtLog(real); // subclasandu-ti clasa ta
    Callback callback = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Calling " + method.getName() + " with args " + Arrays.toString(args));
        return method.invoke(real, args); // las apelul sa mearga la metoda tinta!
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, callback); // subclasa dinamica
    // nu exista bytecodeul acestei clase generate in /target in vreun fisier ci e totul in memorie la apel
    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}

// exact ce genereaza springul cu CGLIB care acronim vine de la Code Generation Library
class SubclasaPtLog extends Maths {
  private final Maths delegate;
  public SubclasaPtLog(Maths delegate) {
    this.delegate = delegate;
  }

  @Override
  public int sum(int a, int b) {
    System.out.println("sum " + a + " and " + b);
    return delegate.sum(a, b);
  }
  @Override
  public int product(int a, int b) {
    System.out.println("product " + a + " and " + b);
    return delegate.product(a, b);
  }
}
// orice metoda se cheama in Maths, logeaza-i parametrii, fara sa modifici nimic sub linie
// ------------------------ THE LINE ------------------
@Service
class SecondGrade {
  private final Maths maths;

  SecondGrade(Maths maths) {
    this.maths = maths;
  }

  public void mathClass() {
//    Maths maths = new Maths(); // nu mai iei dep injectata4
    System.out.println("Miciuna: spring mi-a injectat un ... " + maths.getClass().getSimpleName());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
/*final:CRASH*/ // de aia nici nu ai voie sa faci bean spring record
@Service
class Maths {
//  @Secured("ROLE_ALEX_ILIE")
  public /*static:ignore*/ int sum(int a, int b) { // nu merge interceptia pe Util/Hellper
    //new RuntimeException("Intentionat").printStackTrace();
    return a + b;
  }
  @Log
  public /*final:weirdBug*/ int product(int a, int b) {
//    return a * b;
    int result = 0;
    for (int i = 0; i < b; i++) {
      //?? TOP TZEAPA: apelul local in cadrul aceleiasi clase nu e interceptat
      result = sum(result, a);
    }
    return result;
  }
}
// Concluzii: nu merg proxy daca: cu ce m-am ales in viata
// - nu faci clasa beanului final, nu record; dar DA campurile finale!
// - nu faci metodele din beanuri final sau static
// - nu faci 'new' la beanuri manageuite de spring


// magia asta de proxy cand e folosita de Spring? la adnotari pe metode
// @Transactional = proxy care incepe si commituieste tranzactia
// @Async = executa metoda pe alt thread
// @Secured("ROLE_ADMIN"),@RolesAllowed,@PreAuthorized = verifica autorizarea
// @Cacheable = verifica cacheul
// @Retryable = reincearca apelul metodei in caz de exceptie
// @Validated = valideaza parametrii metodei
// @Timed = masoara timpul de executie iese apoi pe http://localhost:8080/actuator/metrics
// @Log (facuta de orange)


// ==sa nu confunzi cu @ pt IoC = cand te cheama frameworkul pe tine
// @Scheduled = proxy care executa metoda la interval regulat
// @GetMapping
// @PostConstruct
// @EventListener


// si Mockito.mock(X.class) e un proxy sau @Mock