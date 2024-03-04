package victor.training.spring.first;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static victor.training.spring.first.Custom.*;

@Constraint(validatedBy = CustomValidator.class)
public @interface Custom {
  class CustomValidator implements ConstraintValidator<Custom, String>{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      return false;
    }

  }
}

