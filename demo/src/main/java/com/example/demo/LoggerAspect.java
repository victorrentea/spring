package com.example.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggerAspect {

  //  @Before("execution(* com.example..*.*DAO(..))") << nu recomand
  @Around("@within(Logged) || @annotation(Logged)") // 💖 clase sau metode adnotate
  public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println(
        joinPoint.getSignature().getDeclaringTypeName()
        + "." + joinPoint.getSignature().getName() +
        " is called with " +
        Arrays.toString(joinPoint.getArgs()));
    long t0 = System.currentTimeMillis();
    Object r = joinPoint.proceed();
    long t1 = System.currentTimeMillis();
    System.out.println("Result is: " + r+ ". Execution time: " + (t1 - t0) + "ms");
    return r;
  }
}
