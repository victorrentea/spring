package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect // new !
@Component
@Slf4j
public class LoggingAspectExercise {

//  @Around("execution(* victor.training.spring.aspects.Maths.*(..))") // ugly don't!
//  @Around("execution(* victor.training..*.*(..))") // dark magic
  @Around("@annotation(LoggedMethod) || @within(LoggedMethod)") // nice
  public Object intercept(ProceedingJoinPoint point) throws Throwable {
    String methodName = point.getSignature().getName();
    System.out.println("Calling " + methodName + " with " + Arrays.toString(point.getArgs()));

    // call real method
    var result = point.proceed();

    System.out.println("Returned " + result);
    return result;
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
// @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.delete*(..))") // all subtypes of JpaRepository
