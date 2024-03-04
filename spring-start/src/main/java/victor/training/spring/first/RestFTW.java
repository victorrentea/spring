package victor.training.spring.first;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestFTW {
  record Dto(
      @NotNull @NotBlank String name, // immutables version: non-optional property
      @Email String email,
      @NotNull @Min(18) Integer age) {
  }
  @PostMapping("/hello")
  public String hello(@Validated @RequestBody Dto dto) {
    return "Hello " + dto.name + " aged " + dto.age;
  }
}
