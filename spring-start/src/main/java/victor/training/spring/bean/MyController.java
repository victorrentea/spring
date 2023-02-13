package victor.training.spring.bean;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RestController
public class MyController {
  private final WelcomeInfo welcomeInfo;

  @GetMapping("hello")
  public String hello() { // /hello
    return welcomeInfo.getWelcomeMessage();
  }
}
