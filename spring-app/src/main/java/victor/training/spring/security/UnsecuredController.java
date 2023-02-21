package victor.training.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;

@RestController
public class UnsecuredController {

  @Autowired
  private WelcomeInfo welcomeInfo;

  // TODO allow unsecured access
  @GetMapping("unsecured/welcome")
  public WelcomeInfo showWelcomeInfo() {
    return welcomeInfo;
  }
}
