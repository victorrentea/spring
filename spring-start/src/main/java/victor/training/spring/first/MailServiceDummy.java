package victor.training.spring.first;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// pe local nu vreau sa trimit mailuri
@Primary
@Component
@Profile("dev")
public class MailServiceDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("MailServiceDummy: faking send of " + subject);
  }
}
