package victor.training.spring.first;

import org.springframework.stereotype.Service;

// TODO use for local dev only
@Service
public class MailServiceDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("MailServiceDummy: faking send of " + subject);
  }
}
