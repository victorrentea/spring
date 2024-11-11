package victor.training.spring.first;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

// TODO for local dev only
@Primary
@Profile("local")
@Service
public class MailServiceDummy
    implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY SENDER: " + subject);
  }
}
