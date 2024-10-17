package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.streams.KafkaStreamsMicrometerListener;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@ConditionalOnProperty(
    name = "logging.on",
    havingValue = "true",
    matchIfMissing = true)
@ConditionalOnClass(name="org.springframework.kafka.streams.KafkaStreamsMicrometerListener")
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

  @Order(2)
//  @Around("execution(* victor.training.spring.aspects.Maths.*(..))")
//  @Around("execution(* *.get*(..))") // all methods whose name start with "get"!! = naming convention = dangerous😱
  @Around("@annotation(LoggedMethod) || @within(LoggedMethod)") // methods or classes annotated with @LoggedMethod
  public Object intercept(ProceedingJoinPoint point) throws Throwable {
    Object r = point.proceed();
    log.info("Method {} called with args {} returned {}", point.getSignature().getName(), point.getArgs(), r);
    return r;
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
