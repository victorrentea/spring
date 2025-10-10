package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspectExercise {
  // TODO 0: Run ProxySpringApp.main() -> if you see 6 + 6 = 12 in the log you're OK

  // TODO 1 print 'INTERCEPTED' before every call to methods of Maths
  //  - use @Around("execution(* victor.training.spring..*.*(..))")
  //      to intercept any method of any class in my package
  //  - the function should take a ProceedingJoinPoint parameter
  //  - call ProceedingJoinPoint#proceed() and return its result

  // TODO 2 print method name and arguments, extracted from the ProceedingJoinPoint

  // TODO 3 print the value returned by ProceedingJoinPoint#proceed()

  // TODO 5 Target the interception via @Logged annotation (defined in this package)
  //   - @Around("@annotation(Logged)") targets methods annotated with @Logged
  //   - @Around("@within(Logged)") targets methods in classes annotated with @Logged
  //   - @Around("@within(Logged) || @annotation(Logged)") -> ⭐️ methods and/or classes annotated with @Logged
  public void intercept() {
    log.info("INTERCEPTED");
  }
}
// @Around("@within(RestController)") // method of classes annotated with @RestController
// @Around("@annotation(LoggedMethod)") // methods annotated with @LoggedMethod
// @Around("@annotation(LoggedMethod) || @within(LoggedMethod)") // methods or classes annotated with @LoggedMethod
// -- DANGER ZONE --
// @Around("execution(* victor.training.spring..*.*(..))") // any method of any class in a sub-package of 'web'
// @Around("execution(* *.get*(..))") // all methods whose name start with "get"!! = naming convention = dangerous😱
// @Around("execution(* victor.training.spring.aspects.Maths.sum(..))") // 100% specific -> over-engineering?
//    aspects should be applied in MANY places => push logic INSIDE that method or to an earlier method calling it

// also useful:
// @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))") // all subtypes of JpaRepository
