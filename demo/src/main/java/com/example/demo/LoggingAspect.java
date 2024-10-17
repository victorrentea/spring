package com.example.demo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Configurable;


@Aspect
//@Configurable // pure 👿 evil!! never use this
public class LoggingAspect {
    @Around("@annotation(LogExceptions)")
    public Object logMessage(ProceedingJoinPoint point) throws Throwable {
        try {
            return point.proceed();
        } catch (Throwable e) {
            System.out.println("we have an error ! "+ e.getMessage());
            throw e;
        }
    }
}


//"@annotation(LogExceptions)"