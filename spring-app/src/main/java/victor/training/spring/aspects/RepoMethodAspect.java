package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class RepoMethodAspect {
    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    public Object interceptRepoMethod(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("Calling repo method: {}.{}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        return pjp.proceed();
    }
}
