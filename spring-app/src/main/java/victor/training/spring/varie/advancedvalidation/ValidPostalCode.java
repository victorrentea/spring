package victor.training.spring.varie.advancedvalidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =  PostalCodeValidator.class)
public @interface ValidPostalCode {
    Class<?>[] groups() default { };
    String message() default "{javax.validation.constraints.ValidPostalCode.message}";
    Class<? extends Payload>[] payload() default { };

}
