package victor.training.spring.aspects;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

@Slf4j
@Aspect
@Order(-12415)
@Component
public class LoggingAspectExercise {
//    @Around("@within(victor.training.spring.aspects.Facade)") // all methods of a CLASS annotated with @Facade
//    @Around("@annotation(victor.training.spring.aspects.LoggedMethod)") // @LoggedMethod on the METHOD
//    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))") // all subtypes of JpaRepo
        // danger zone --
//    @Around("execution(* victor.training.spring..*.*(..))") // all methods in a package, riscant
//    @Around("execution(* *.get*(..))") // all methods named get* -> too much?
//    @Around("execution(* victor.training.spring.aspects.Maths.sum(..))") // 100% specific -> overengineering?

    // Run ProxyIntroApp.main() to test it.
    // TODO 1 print 'INTERCEPTED' before every call to any method inside Maths
    //  Hint: use any @Around above
    //  Hint: the function should take a ProceedingJoinPoint parameter
    //  Hint: call joinPoint#proceed and return its result out
    // TODO 2 add to the print: the method name, arguments and return value
    //  Hint: extract them from the ProceedingJoinPoint parameter
    // TODO 3 print the returned value
    //  Hint: call the ProceedingJoinPoint#proceed() to get it

    // abuz sa faci magie de genul asta doar pentru 1-2 metode
//    @Around("execution(* victor.training.spring.aspects.Maths.*(..))")
//    @Around("@within(Facade)")
    @Around("@annotation(LoggedMethod)")
    public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
//        log.info("INTERCEPTED");
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        log.info("Calling {} with params {}", methodName, Arrays.toString(args));

//        Method method = pjp.getSignature().getDeclaringType().getDeclaredMethod(methodName);
//        Retry annotation = method.getAnnotation(Retry.class);
//        int count = annotation.count;
//        for (int i = 0; i < count; i++) {
//
//        }
        Object result = pjp.proceed();

        log.info("returned: " + result);
        return result;
    }
}