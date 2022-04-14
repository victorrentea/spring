package victor.training.spring.web.security;

import victor.training.spring.web.entity.UserRole;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface VisibleFor {
   UserRole[] value();
}
