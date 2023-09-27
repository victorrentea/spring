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
public class ScopePrototype { // = 1
//  @Autowired
//  private DataValidator dataValidator; // daca intr-un singleton injectezi un @Scope("prototype")
  @Autowired
  private ApplicationContext applicationContext; // interfata catre obiectele manageuite de spring

  @GetMapping("api/scope/prototype")
  public /*synchronized - NICIODATA */ List<ValidationMessage> requestScope() {
    String foo = "some text to validate";
    String bar = "a pub";
    DataValidator dataValidator = applicationContext.getBean(DataValidator.class); // la fiecare apel din asta, se face 'new' DataValidator
//    DataValidator dataValidator = new DataValidator();
    // cere si ti se va da (o noua instanta de fiecare data)
    dataValidator.getValidationMessages().clear();
    ThreadUtils.sleepMillis(3000); // allow the race
    altaMetoda(dataValidator, foo, bar);
    return dataValidator.getValidationMessages();
  }

  private static void altaMetoda(DataValidator dataValidator, String foo, String bar) {
    dataValidator.validateFoo(foo);
    dataValidator.validateBar(bar);
  }
}
@Component
@Scope("prototype") // ori de cate ori Spring are nevoie de o instanta din asta creaza o instanta noua
class DataValidator {
//  @Autowired // asta incepe sa faca sa merite "prototype"
//  private AlteChestii alteChestii;
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