package victor.training.spring.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnsecuredController {

  // TODO allow unsecured access
  @GetMapping("unsecured/welcome")
  public String showWelcomeInfo() {
    return "Halo!";
  }
}
