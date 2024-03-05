package victor.training.spring.first.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component // can be scanned in a package of a library you imported by mistake
public class LoggingAspectExercise {
  // aspect that works even if LOggedMethod is put on an annotation used on the intercepted class
  @Around("@annotation(LoggedMethod) || @within(LoggedMethod)") // tweak this as a homework
  public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("INTERCEPTED METHOD CALL: " +
             pjp.getSignature().getName() + " with args: " +
             Arrays.toString(pjp.getArgs()));
    var r = pjp.proceed();// call the original method

    // danger: dev not aware of @Aspect
    // danger: performance-wise: if NETWORK CALLS
    // 'synchronized' - crimial act on Reavtive and/or Java 21
    //
    return r;
  }
}







// HOMEWORK: play with all the following:....
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
