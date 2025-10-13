package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspectExercise {
  // TODO 0: Run ProxySpringApp.main() -> you should see in log 6 + 6 = 12

  // TODO 1 print 'INTERCEPTED' before every call to methods of Maths
  //  - use @Around("@annotation(Logged)") to intercept any method annotated with @Logged
  //     The @Logged annotation is defined in this package
  //  - the function should take a ProceedingJoinPoint parameter
  //  - call ProceedingJoinPoint#proceed() and return its result

  // TODO 2 print method name and arguments, extracted from the ProceedingJoinPoint

  // TODO 3 print the value returned by ProceedingJoinPoint#proceed()

  // TODO 4 ⭐️ make this aspect also target all methods in classes annotated with @Logged
  //   - use @Around("@within(Logged) || @annotation(Logged)")
  public void intercept() {
    log.info("INTERCEPTED");
  }
}



// TODO 5: also include the time the method took to execute
// TODO 6: @Logged(LogLevel.TRACE) should log on 'trace' level, @Logged(INFO) on info...


// more details
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
