package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

@Slf4j
@Aspect
@Component
// in platform code, how to make this class disable-able
//@Profile("aspects")

// Convention over Configuration (CoC)
//@ConditionalOnProperty(name = "aspects.enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnExpression("${aspects.enabled:true}") // SPEL
public class LoggingAspect {

  // TODO 0: Run ProxySpringApp.main() -> you should see in log 6 + 6 = 12

  // TODO 1 print 'INTERCEPTED' before every call to methods of Maths
  //  - use @Around("@annotation(Logged)") to intercept any method annotated with @Logged
  //     The @Logged annotation is defined in this package
  //  - the function should take a ProceedingJoinPoint parameter
  //  - call ProceedingJoinPoint#proceed() and return its result

  // TODO 2 print method name and arguments, extracted from the ProceedingJoinPoint

  // TODO 3 print the value returned by ProceedingJoinPoint#proceed()

  // TODO 4 ⭐️ also intercept all methods in classes annotated with @Logged
  //   - use @Around("@within(Logged) || @annotation(Logged)")
  // intercept all methods directly annotated with @Logged, or in classes annotated with @Logged
  @Around("@annotation(victor.training.spring.aspects.Logged) || @within(victor.training.spring.aspects.Logged)")
  public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
    log.info("INTERCEPTED: {} with args: {}", pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));
    long t0 = currentTimeMillis();
    var r= pjp.proceed(); // real method call
    long t1 = currentTimeMillis();
    return r;
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

