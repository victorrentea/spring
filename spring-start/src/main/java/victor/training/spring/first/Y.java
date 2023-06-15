package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Y {
//  @Qualifier("mailServiceImpl") // springule vreau acea isntanta cu numele asta
  private final MailService mailService; // polymorphic injection

  //  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message = "HALO";

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
  public Y(MailService mailService) {
    this.mailService = mailService;
  }

  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);

    return 1;
  }
}
