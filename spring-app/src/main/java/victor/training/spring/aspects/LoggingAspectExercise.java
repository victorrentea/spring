package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect // !!! spune Springului ca in aceasta clasa voi defini interceptii de metode
@Component
@Order(100)
public class LoggingAspectExercise {
//  @Before
//  @After
//  @Around("execution(* victor.training.spring.aspects.Maths.*(..))")
//  @Around("execution(* victor.training.spring.aspects.*.*(..))")
  @Around("execution(* victor..*.*(..))")
  public Object intercept(ProceedingJoinPoint point) throws Throwable {
    var r= point.proceed(); // chem metoda reala
    System.out.println("Call intercepted: " +
                       point.getSignature().getName()
                       + Arrays.toString(point.getArgs()) + "=" + r);
    return r;
  }
}
