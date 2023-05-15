package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Methods in a class annotated with @Facade
 *
 * @Around("@within(victor.training.spring.aspects.Facade))") <p>
 * Methods annotated with @LoggedMethod:
 * @Around("@annotation(victor.training.spring.aspects.LoggedMethod))") <p>
 * Methods of all subtypes of JpaRepository (ie all your Spring Data JPA Repos
 * @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
 * <p>
 * Methods of any class in a sub-package of 'web'
 * @Around("execution(* victor.training.spring.web..*.*(..))")
 * <p>
 * Methods starting with "get" in any class = too much? 😱
 * @Around("execution(* *.get*(..))")
 */
@Slf4j
@Aspect
@Component
public class LoggingAspectExercise {
    // TODO 1 log a message for any call to some methods, using any of the techniques above
    // TODO 2 print the method name, arguments and return value to the console
//  @Around(...)
    public void intercept() { // TODO
        // TODO
    }
}