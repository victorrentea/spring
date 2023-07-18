package victor.training.spring.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME) // stops javac from removing it at compilation
@PreAuthorize("hasAnyRole('ADMIN','POWER')")
// doar daca se repeta in mai multe locuri
public @interface CanDeleteTraining {
}
