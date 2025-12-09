package victor.training.spring.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.scope.ValidationMessage.Severity;
import victor.training.spring.varie.Sleep;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ScopePrototype {
    @Autowired
    private DataValidator dataValidator;

    @GetMapping("api/scope/prototype")
    public List<ValidationMessage> requestScope() {
        String foo = "some text to validate";
        String bar = "a pub";
        dataValidator.getValidationMessages().clear();
        Sleep.millis(3000); // allow the race
        dataValidator.validateFoo(foo);
        dataValidator.validateBar(bar);
        return dataValidator.getValidationMessages();
    }
}

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

record ValidationMessage(Severity severity, String message) {
    enum Severity {
        ERROR,
        WARNING,
        INFORMATION
    }

}