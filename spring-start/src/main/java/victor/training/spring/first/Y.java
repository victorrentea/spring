package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.supb.X;

@Service
public class Y {
private final X x;

  @Qualifier("mailServiceImpl")
  private final MailService mailService; // polymorphic injection
//  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message = "HALO";

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
  public Y(@Lazy X x, MailService mailService) {
    this.x = x;
    this.mailService = mailService;
  }

  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);

    return 1;
  }
}
