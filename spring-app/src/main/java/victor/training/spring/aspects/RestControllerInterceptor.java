package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

/**
 * vreau: sa interceptez si sa logez metoda si param pentru orice apel
 *  catre o clasa
 * - din victor.training.spring.web.controller
 * - @Logged
 */
@Component
@Aspect
@Slf4j
public class RestControllerInterceptor {

//    @Around("execution(* victor.training.spring.web.controller.*.*(..))")
    @Around("@within(victor.training.spring.aspects.Logged)") // clasa sa aiba adnotare
//    @Around("@annotation(victor.training.spring.aspects.Logged)") // metoda sa aiba adnotare
//    @Around("@annotation(victor.training.spring.aspects.Logged) || @within(victor.training.spring.aspects.Logged)") // metoda sa aiba adnotare
    public Object logheaza(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Invoc metoda {} cu param {}",
                pjp.getSignature().getName(),
                Arrays.toString(pjp.getArgs()
                ));
        Object r = pjp.proceed();
        return r;
    }
}
