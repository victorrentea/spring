package victor.training.spring.varie.advancedvalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PostalCodeValidator.class)
public @interface ValidPostalCode {
  Class<?>[] groups() default {};

  String message() default "{jakarta.validation.constraints.ValidPostalCode.message}";

  Class<? extends Payload>[] payload() default {};

}
