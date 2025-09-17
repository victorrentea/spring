package victor.training.spring.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
// ATENTIE☣️☣️☣️☣️☣️☣️☣️☣️☣️
// NU SCRIE ASTFEL DE CLASA FARA REVIEW DE LA 2 x Elders
public class LoggingAspect {
  private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

  @Around("@annotation(Logged) || @within(Logged)")
  //executia oricarei metode din orice clasa
//  @Around("execution(* victor.training..*.*(..))")
  public Object logCall(ProceedingJoinPoint pjp) throws Throwable {
    long start = System.currentTimeMillis();
    try {
      Object result = pjp.proceed();
      // API -call
      log.info("{}({}) -> {} ({} ms)",
          pjp.getSignature(),
          Arrays.toString(pjp.getArgs()),
          result,
          System.currentTimeMillis() - start);
      return result;
    } catch (Throwable ex) {
      log.warn("{}({}) !! {} ({} ms)",
          pjp.getSignature(),
          Arrays.toString(pjp.getArgs()),
          ex.toString(),
          System.currentTimeMillis() - start);
      throw ex;
    }
  }
}