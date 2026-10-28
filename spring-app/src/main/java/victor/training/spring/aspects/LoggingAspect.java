package victor.training.spring.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
  @Around("@within(Logged) || @annotation(Logged)") // metodele adnotate @Logged sau toate metodele unei clase adnotate @Logged
  public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("Calling " + pjp.getSignature().getName() + "(" + Arrays.toString(pjp.getArgs()) + ")");
    long t0 = System.currentTimeMillis();
    Object result = pjp.proceed(); //call to intercepted method
    long t1 = System.currentTimeMillis();
    System.out.println(pjp.getSignature().getName() + " returned " + result + " in " + (t1 - t0) + " ms");
    return result;
  }
}
