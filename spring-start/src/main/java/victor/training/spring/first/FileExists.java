package victor.training.spring.first;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.io.File;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

// custom validation annotation
@Constraint(validatedBy = FileExists.FileExistsValidator.class)
@Retention(RUNTIME) // stops javac from removing it at compilation
public @interface FileExists {
   String message() default "File does not exist";
   boolean emptyIsOk() default false;
   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
   class FileExistsValidator implements ConstraintValidator<FileExists, File> {
      @Override
      public boolean isValid(File value, ConstraintValidatorContext context) {
          return value.exists();
//         return value.isFile() && (context. .value.length() >=0);
         // sa accepte fisiere goale doar daca emptyIsOk=true
         // obtine valoarea din anotare
//         boolean emptyIsOk = context.getConstraintDescriptor().getAttributes().get("emptyIsOk").equals(true);
//          return value.exists() && (value.length() > 0 || emptyIsOk);
      }
   }
}

