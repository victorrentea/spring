package victor.training.spring.varie.advancedvalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;
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
