package victor.training.spring.first.aop;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME) // prevents javac from removing the ann at compilation
@Inherited
public @interface LoggedMethod {
}
