package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j //✅
@Aspect //AOP: springule, vezi ca mai jos am functii care intercepteaza alte functii #rai$e
@Component //✅
public class LoggingAspect {
  @Around("@within(victor.training.spring.aspects.Logged) || @annotation(victor.training.spring.aspects.Logged)")
  public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
    log.info("INTERCEPTED {}{}", pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));
    Object result = pjp.proceed();
    log.info("RETURNED {}", result);
    return result;
  }
}

// TODO 5: also print the time the method took to execute


// === AspectJ Pointcut Handbook ===
// @Around("@within(RestController)") // method of classes annotated with @RestController
// @Around("@annotation(LoggedMethod)") // methods annotated with @LoggedMethod
// @Around("@annotation(LoggedMethod) || @within(LoggedMethod)") // methods or classes annotated with @LoggedMethod
// @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))") // all subtypes of JpaRepository
// -- DANGER ZONE --
// @Around("execution(* victor.training.spring..*.*(..))") // any method of any class in a sub-package of 'web'
// @Around("execution(* *.get*(..))") // all methods whose name start with "get"!! = naming convention = dangerous😱
// @Around("execution(* victor.training.spring.aspects.Maths.sum(..))") // 100% specific = over-engineering?
