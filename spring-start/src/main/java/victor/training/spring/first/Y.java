package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Y {
  private final MailService mailService; // polymorphic injection

  public Y(MailService mailService) {
    this.mailService = mailService;
  }
  @PostConstruct
  public void logic() {
    mailService.sendEmail("ceva");
  }
}
