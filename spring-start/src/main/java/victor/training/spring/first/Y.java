package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Y {
  private final MailService mailService; // polymorphic injection


  public Y(@Qualifier("mailServiceImpl") MailService mailService) {
    this.mailService = mailService;
  }

  public int logic() {
    mailService.sendEmail("I like 4 topics");
    return 1;
  }
}
