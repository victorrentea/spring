package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Y {
  @Qualifier("mailServiceImpl") // springule vreau acea isntanta cu numele asta
  private final MailService mailService; // polymorphic injection

  @Value("${mail.sender}")
  private final String mailSender;

  //  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message = "HALO";

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
//  public Y(@Qualifier("mailServiceImpl") MailService mailService,
//           @Value("${mail.sender}") String mailSender) {
//    this.mailService = mailService;
//    this.mailSender = mailSender;
//  }

  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);
    System.out.println("Prop citit " + mailSender);
    return 1;
  }
}
