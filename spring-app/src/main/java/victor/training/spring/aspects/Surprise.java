package victor.training.spring.aspects;

import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Service
  @LoggedMethod
@Retention(RUNTIME) // stops javac from removing it at compilation
public @interface Surprise {
}
