package victor.training.spring.first;

import org.springframework.stereotype.Service;

@Service
public class Y {
  private final MailService mailService; // polymorphic injection

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
  public Y(MailService mailService) {
    this.mailService = mailService;
  }

  public int logic() {
    mailService.sendEmail("I like 4 topics");
    return 1;
  }
}
