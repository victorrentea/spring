package victor.training.spring.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sound.midi.Soundbank;
import java.util.Arrays;

/**
 * Methods in a class annotated with @Facade
 * @Around("@within(victor.training.spring.aspects.Facade))")
 * <p>
 * Methods annotated with @LoggedMethod:
 * @Around("@annotation(victor.training.spring.aspects.LoggedMethod))")
 * <p>
 * Methods of all subtypes of JpaRepository (ie all your Spring Data JPA Repos
 * @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
 * <p>
 *   AVOID:
 * Methods of any class in a sub-package of 'web'
 * @Around("execution(* victor.training.spring.web..*.*(..))")
 * <p>
 * Methods starting with "get" in any class = too much? 😱
 * @Around("execution(* *.get*(..))")
 */
@Aspect
@Component
public class LoggingAspectExercise {
  // TODO 1 log a message for any method called in 'Maths' class, using any of the techniques above
  // - make this class an @Aspect
  // - make sure Spring detects this class
  // - create a method that should be executed instead of the original method that you intercept
  // - annotate this new method with @Around -> turns yellow in IntelliJ > ALT-ENTER
  // - let IntelliJ tell you what to do mnext (you'll have to take an ProceedingJoinPoint argument)
  // TODO 2 print the method name, arguments and return value to the console
  @Around("@within(victor.training.spring.aspects.LoggedMethod)" +
          " || @annotation(victor.training.spring.aspects.LoggedMethod) ")

  public Object method(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("Intercepting " + pjp.getSignature().getName() + " with params " + Arrays.toString(pjp.getArgs()));
    Object r = pjp.proceed();
    System.out.println("Result: " + r);
    return r;
  }
  // TO TEST, run ProxyIntro.main() and see the output
}

@Component
class LoggingAspectDemo {
  @PostConstruct
  public void startup() {
    // TODO inject a dependency in this class and call a method to prove the aspect above works
  }
}