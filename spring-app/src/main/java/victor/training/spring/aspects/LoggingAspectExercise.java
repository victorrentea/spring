package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

@Slf4j
@Aspect // custom made!!! CU GRIJA. NU EXAGERATI SI CERETI REVIEW DE LA UN SR.
@Component
public class LoggingAspectExercise {
  // TODO 0: Run ProxySpringApp.main()
  //  - if you see 6 + 6 = 12 in the log you're OK
  // TODO 1 print 'INTERCEPTED' before every call to methods of Maths
  //  - use @Around("execution(* victor.training.spring..*.*(..))")
  //      to intercept any method of any class in my app
  //  - the function should take a ProceedingJoinPoint parameter
  //  - call ProceedingJoinPoint#proceed() and return its result
  // TODO 2 print the method name and arguments
  //  - extract them from the ProceedingJoinPoint parameter
  // TODO 3 print the returned value
  //  = the value returned by #proceed()
  // TODO 4 (optional) experiment with other @Around annotations below
//  @Around("execution(* victor.training.spring..*.*(..))") // cam mult, de evitat
//  @Around("@annotation(LoggedMethod)") // pe metoda
  @Around("@within(LoggedMethod)") // adnotarea pe clasa
  public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
    log.info("INTERCEPTED " + pjp.getSignature().getName() + " cu param " + Arrays.toString(pjp.getArgs()));
    long t0 = currentTimeMillis();
    Object r = pjp.proceed();
    long t1 = currentTimeMillis();
    log.info("RETURN took{}  : " + r , t1-t0);
    return r;
  }

}
// @Around("@within(Facade)") // method of classes annotated with @Facade
// @Around("@annotation(LoggedMethod)") // methods annotated with @LoggedMethod
// -- DANGER ZONE --
// @Around("execution(* victor.training.spring..*.*(..))") // any method of any class in a sub-package of 'web'
// @Around("execution(* *.get*(..))") // all methods whose name start with "get"!! = naming convention = dangerous😱
// @Around("execution(* victor.training.spring.aspects.Maths.sum(..))") // 100% specific -> over-engineering?
//    aspects should be applied in MANY places => push logic INSIDE that method or to an earlier method calling it

// also useful:
// @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))") // all subtypes of JpaRepository
