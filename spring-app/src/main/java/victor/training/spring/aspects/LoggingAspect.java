package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
//    @Around("@within(victor.training.spring.aspects.Facade)") // all methods of a CLASS annotated with @Facade
//    @Around("@annotation(victor.training.spring.aspects.LoggedMethod)") // @LoggedMethod on the METHOD
//    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))") // all subtypes of JpaRepo
        // danger zone --
//    @Around("execution(* victor.training.spring..*.*(..))") // all methods in a package
//    @Around("execution(* *.get*(..))") // all methods named get* -> too much?
//    @Around("execution(* victor.training.spring.aspects.Maths.sum(..))") // 100% specific -> overengineering?

    // Run ProxyIntroApp.main() to test it.
    // TODO 1 print 'INTERCEPTED' before every call to any method inside Maths
    //  Hint: use the last @Around above
    //  Hint: the function should take a ProceedingJoinPoint parameter
    //  Hint: call joinPoint#proceed and return its result out
    // TODO 2 add to the print: the method name, arguments and return value
    //  Hint: extract them from the ProceedingJoinPoint parameter
    // TODO 3 print the returned value
    //  Hint: call the ProceedingJoinPoint#proceed() to get it
    // TODO 4 try every @Around forms above
    public void intercept() {
        log.info("INTERCEPTED");
    }
}