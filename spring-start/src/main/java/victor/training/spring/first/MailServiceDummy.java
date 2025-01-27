package victor.training.spring.first;

import org.springframework.stereotype.Service;

@Service // ASTA💖
public class MailServiceDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY SENDER: " + subject);
  }
}
