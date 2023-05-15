package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Y {
  private final MailService mailService; // polymorphic injection

  public int logic() {
    mailService.sendEmail("I like 4 topics");
    return 1;
  }
}
