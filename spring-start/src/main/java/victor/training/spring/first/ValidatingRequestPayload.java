package victor.training.spring.first;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidatingRequestPayload {
  record Request(
      @NotNull
      String name,
      @Min(0)
      @NotNull
      Integer age) {
  }

  @PostMapping("/validate")
  public void method(
      @Validated @RequestBody Request request) {
    System.out.println("Received: " + request);
  }
}
