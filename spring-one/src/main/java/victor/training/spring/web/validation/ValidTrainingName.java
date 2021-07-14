package victor.training.spring.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Size(min = 3, max = 50)
@Constraint(validatedBy = {})
@Documented
@Retention(RUNTIME)
public @interface ValidTrainingName { // Declared constraint, reusable in Dtos AND in Entities

   String message() default "Wrong zip code";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};

}
