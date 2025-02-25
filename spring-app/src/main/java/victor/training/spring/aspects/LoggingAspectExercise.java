package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
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

//  @Before("@annotation(Logged) || @within(Logged)")
  @Around("@annotation(Logged) || @within(Logged)")
  public Object intercept(ProceedingJoinPoint joinPoint) throws Throwable {
   log.info("{} ({})", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
   Object r = joinPoint.proceed();//call real method
   log.info("Returned: {}", r);
   return r;
  }
}

// @Around("@within(RestController)") // method of classes annotated with @RestController
// @Around("@annotation(LoggedMethod)") // methods annotated with @LoggedMethod
// @Around("@annotation(LoggedMethod) || @within(LoggedMethod)") // methods or classes annotated with @LoggedMethod
// -- DANGER ZONE --
// @Before("execution(* victor.training.spring.aspects.Maths.*(..))") // 100% specific -> over-engineering?
// @Around("execution(* victor.training.spring..*DAO.*(..))") // any method of any class in a sub-package of 'web'
// @Around("execution(* *.get*(..))") // all methods whose name start with "get"!! = naming convention = dangerous😱
//    aspects should be applied in MANY places => push logic INSIDE that method or to an earlier method calling it

// also useful:
// @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))") // all subtypes of JpaRepository
