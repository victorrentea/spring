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
public class LoggingAspectExercise {
  @Around("execution(* victor..*.*(..))") // inseamna orice metoda din orice clasa din orice subpachet al lui victor
  public Object intercept(ProceedingJoinPoint joint) throws Throwable {
    System.out.println(joint.getSignature().getName() +
                       " " + Arrays.toString(joint.getArgs()));
    Object r = joint.proceed();// apeleaza metoda interceptata
    System.out.println("Result: " + r);
    return r;
  }
}
