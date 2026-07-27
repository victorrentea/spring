package victor.training.spring.first;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Component
@Retention(RUNTIME) // survives javac
public @interface CamelProcessor {
}
