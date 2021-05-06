package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1)
@Aspect
@Component
public class LoggingInterceptor {
//   @Around("execution(* victor..*.*(..))")
//   @Around(" @within(victor.training.spring.aspects.Logged)")
   @Around("@annotation(victor.training.spring.aspects.Logged)")
   public Object interceptAndLog(ProceedingJoinPoint pjp) throws Throwable {
      log.info("Calling method " + pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName());
      return pjp.proceed();
   }
}
