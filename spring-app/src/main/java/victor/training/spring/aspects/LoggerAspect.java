package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.util.Arrays;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Slf4j
@Component
@Aspect
public class LoggerAspect {
  // stil anii 90s
//  @Around("execution(* victor.training..*.*(..))") // inseamna : intercepteaza toate metodele din toate clasele din package-ul victor.training
  @Around("@annotation(Log)") // intercepteaza toate metodele care au adnotarea @Log
  public Object log(ProceedingJoinPoint pjp) throws Throwable {
    log.info("Calling method: " + pjp.getSignature().getName() +
             " with args: " + Arrays.toString(pjp.getArgs()));
    Object r = pjp.proceed(); // chem metoda reala
    return r;
  }
}
@Retention(RUNTIME) // nu-ti sterge javac adnotarea la compilar
@interface Log {
}
