package victor.training.spring.props;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

@Component
public class AutoValidateBeanFields implements BeanPostProcessor {
  private final Validator validator;

  public AutoValidateBeanFields(Validator validator) {
    this.validator = validator;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    boolean needsValidation = hasValidationAnnotationsOnFields(bean);
    if (needsValidation) {
      System.out.println("Found bean containing jakarta.validation annotations on fields :  " + beanName);
      Set<ConstraintViolation<Object>> violations = validator.validate(bean);
      if (!violations.isEmpty()) {
        throw new ConstraintViolationException("Bean " + beanName + " failed validation of its fields: " + violations, violations);
      }
    }
    return bean;
  }

  private static boolean hasValidationAnnotationsOnFields(Object bean) {
    return Arrays.stream(bean.getClass().getDeclaredFields())
            .anyMatch(AutoValidateBeanFields::hasValidationAnnotations);
  }

  private static boolean hasValidationAnnotations(Field field) {
    try {
      field.setAccessible(true); // ignore "private"
      boolean hasJavaxValidations = Arrays.stream(field.getAnnotations())
              .anyMatch(ann -> ann.annotationType().getCanonicalName().startsWith("jakarta.validation"));
      if (hasJavaxValidations) return true;
    } catch (Exception e) {
      // swallow ex
    }
    return false;
  }
}
