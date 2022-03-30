package victor.training.spring.web;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Order(1)
public class MyLoggingAspect {
   private static final Logger log = LoggerFactory.getLogger(MyLoggingAspect.class);

//   @Around("execution(* victor.training.spring.web.service..*.*(..))") // avoid package patterns
   @Around("@within(victor.training.spring.web.service.Logged)")
   public Object logsArgs(ProceedingJoinPoint pjp) throws Throwable {
      log.debug("Calling method " + pjp.getSignature().getName() + " with args " + Arrays.toString(pjp.getArgs()));
      Object result = pjp.proceed();
      log.debug("Result was: " + result);
      return result;
   }
}
