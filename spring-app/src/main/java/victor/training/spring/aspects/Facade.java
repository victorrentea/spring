package victor.training.spring.aspects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Service
@Transactional
@Retention(RUNTIME) // stops javac from removing it at compilation
public @interface Facade {
}