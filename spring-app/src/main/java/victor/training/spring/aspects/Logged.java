package victor.training.spring.aspects;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME) // sa n-o stearga javac
public @interface Logged {
}
