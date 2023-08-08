package victor.training.spring.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component // +@Import
public class MyAspect {
//    @Around("execution(* victor..*.get*(..))")
//    @Around("@annotation(LoggedMethod)")
    @Around("@within(Facade)") // the class
    public Object interceptAndLog(ProceedingJoinPoint pjp)
            throws Throwable {
        Object r = pjp.proceed(); //call the real method
        System.out.println(pjp.getSignature().getName() +
                " (" + Arrays.toString(pjp.getArgs()) + ")=" + r);
        return r;
    }
}
