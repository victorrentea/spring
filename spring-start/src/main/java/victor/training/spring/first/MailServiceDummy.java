package victor.training.spring.first;

import org.springframework.stereotype.Component;

@Component
public class MailServiceDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("MailServiceDummy: faking send of " + subject);
  }
}
