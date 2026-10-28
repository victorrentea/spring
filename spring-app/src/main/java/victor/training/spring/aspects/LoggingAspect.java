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
public class LoggingAspect {
  @Around("@within(Logged) || @annotation(Logged)") // metodele adnotate @Logged sau toate metodele unei clase adnotate @Logged
  public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
    log.info("Calling {}({})", pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));
    long t0 = System.currentTimeMillis();
    Object result = pjp.proceed(); //call to intercepted method
    long t1 = System.currentTimeMillis();
    log.info("{} returned {} in {} ms", pjp.getSignature().getName(), result, t1 - t0);
    return result;
  }
}
