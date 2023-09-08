package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RefreshScope
public class Control {

  private final WelcomeInfo welcomeInfo;

  @Value("${welcome.welcome-message}")
  private final String message;

  @GetMapping("welcome")
  public String getWElcome() {
    return welcomeInfo.getWelcomeMessage() + " and " + message;
  }
}

