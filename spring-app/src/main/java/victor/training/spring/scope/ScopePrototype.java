package victor.training.spring.scope;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.scope.ValidationMessage.Severity;
import victor.training.spring.varie.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ScopePrototype {
  //  @Autowired
  //  private DataValidator dataValidator; // risk!
  @Autowired
  private ApplicationContext applicationContext;

  @GetMapping("api/scope/prototype")
  public List<ValidationMessage> requestScope() {
    String foo = "some text to validate";
    String bar = "a pub";
    DataValidator dataValidator = applicationContext.getBean(DataValidator.class);
    dataValidator.getValidationMessages().clear();
    ThreadUtils.sleepMillis(3000); // allow the race
    dataValidator.validateFoo(foo);
    dataValidator.validateBar(bar);
    return dataValidator.getValidationMessages();
  }
}
@Scope("prototype")
@Component
class DataValidator {
  private final List<ValidationMessage> validationMessages = new ArrayList<>();
  public void validateFoo(String foo) {
    someValidation(foo);
    dataValidation(foo);
  }

  private void someValidation(String foo) {
    if (!foo.contains("some")) {
      validationMessages.add(new ValidationMessage(Severity.WARNING, "Some is missing"));
    }
  }

  private void dataValidation(String foo) {
    if (!foo.contains("data")) {
      validationMessages.add(new ValidationMessage(Severity.ERROR, "Data is missing"));
    }
  }

  public void validateBar(String bar) {
    if (!bar.contains("C2H6O")) {
      validationMessages.add(new ValidationMessage(Severity.ERROR, "No alcohol in this bar!"));
    }
  }

  public List<ValidationMessage> getValidationMessages() {
    return validationMessages;
  }
}
@Value
class ValidationMessage {
  enum Severity {
    ERROR,
    WARNING,
    INFORMATION
  }
  Severity severity;
  String message;
}