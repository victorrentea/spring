package com.example.demo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Configurable;


@Aspect
@Configurable
public class LoggingAspect {
    @Around("@annotation(LogExceptions)")
    public Object logMessage(ProceedingJoinPoint point) {
        try {
            return point.proceed();
        } catch (Throwable e) {
            System.out.println("we have an error ! "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}


//"@annotation(LogExceptions)"