package victor.training.spring.first;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.io.File;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


// custom javax.validation annotation
@Constraint(validatedBy = FileExists.FileExistsValidator.class)
@Retention(RUNTIME) // stops javac from removing it at compilation
public @interface FileExists {
   String message() default "File does not exist";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
   class FileExistsValidator implements ConstraintValidator<FileExists, File> {
      @Override
      public boolean isValid(File value, ConstraintValidatorContext context) {
         return value.isFile();
      }
   }
}

