package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LoggingInterceptor {
   @Around("execution(* victor..*.*(..))")
   public Object interceptAndLog(ProceedingJoinPoint pjp) throws Throwable {
      log.info("Calling method " + pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName());
      return pjp.proceed();
   }
}
