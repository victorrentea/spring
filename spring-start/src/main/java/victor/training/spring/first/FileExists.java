package victor.training.spring.first;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.io.File;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

// custom validation annotation - de evitat, prea complicat; poate fi abuzat
@Constraint(validatedBy = FileExists.FileExistsValidator.class)
@Retention(RUNTIME) // stops javac from removing it at compilation
public @interface FileExists { // overengineering (in general)
   String message() default "File does not exist";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
   class FileExistsValidator
       implements ConstraintValidator<FileExists, File> {
      @Override
      public boolean isValid(File file, ConstraintValidatorContext context) {
        // poti folosi aici repo-uri/API calls😱😱😱. ⚠️⚠️⚠️ NU O FACE
         return file.isFile();
      }
   }
}

